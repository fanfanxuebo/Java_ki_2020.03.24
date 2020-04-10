package ki.springmvc.mvcframework.servlet;

import ki.springmvc.mvcframework.annotation.*;
import ki.springmvc.mvcframework.pojo.HandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-05 18:54:27 星期日
 */
public class MyDispatcherServlet extends HttpServlet {

    private Properties properties = new Properties();

    private List<String> classNameList = new ArrayList<>();

    private Map<String, Object> singletonObjects = new HashMap<>();

    private Map<String, HandlerMapping> handlerMappingMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取到请求路径
        String requestUrl = req.getRequestURI();
        // 根据请求路径，找到对应的类和方法
        HandlerMapping handlerMapping = handlerMappingMap.get(requestUrl);
        if (handlerMapping == null) {
            resp.getWriter().write("404 not found");
            return;
        }

        // 校验用户权限
        List<String> securityUserNameList = handlerMapping.getSecurityUserNameList();
        String username = req.getParameter("username");
        if (!securityUserNameList.contains(username)) {
            resp.getWriter().write("sorry! you do not have access permission!");
            return;
        }

        Method method = handlerMapping.getMethod();
        Object controller = handlerMapping.getController();
        Map<String, Integer> paramIndexMapping = handlerMapping.getParamIndexMapping();

        Set<Map.Entry<String, Integer>> entrySet = paramIndexMapping.entrySet();
        Object[] args = new Object[entrySet.size()];

        for (Map.Entry<String, Integer> entry : entrySet) {
            String key = entry.getKey();
            if (HttpServletRequest.class.getSimpleName().equals(key)) {
                args[entry.getValue()] = req;
                continue;
            }
            if (HttpServletResponse.class.getSimpleName().equals(key)) {
                args[entry.getValue()] = resp;
                continue;
            }
            String parameter = req.getParameter(key);
            args[entry.getValue()] = parameter;
        }


        /*Map<String, String[]> reqParameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> entry : reqParameterMap.entrySet()) {
            int index = paramIndexMapping.get(entry.getKey());
            args[index] = entry.getValue();
        }
        int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getSimpleName());
        args[reqIndex] = req;
        int respIndex = paramIndexMapping.get(HttpServletResponse.class.getSimpleName());
        args[respIndex] = resp;*/

        try {
            method.invoke(controller, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 加载配置文件 springmvc.properties
        doLoadConfig(config);
        // 将路径转换为磁盘目录，遍历文件夹，生成全限定类名，放入集合中 classNameList
        doScanForGetClassName(properties.getProperty("scanPackage"));
        // 遍历全限定类名集合，反射创建对象，放入自定义的bean容器中 singletonObjects
        doInstance();
        // 遍历bean容器获取@MyAutowired注解的属性值，实现依赖注入
        doAutowired();
        // 封装RequestMapping，方法名称，方法参数，反射调用方法使用
        initHandlerMapping();

        System.out.println("MyDispatcherServlet.init 初始化完成，开始进入 MyDispatcherServlet.doPost 处理请求");

    }

    /**
     * 将 url 和 method 建立映射关系（标记方法参数位置，封装 MySecurity 注解配置的有权限的用户名）
     */
    private void initHandlerMapping() {
        if (singletonObjects.isEmpty()) return;

        for (Map.Entry<String, Object> entry : singletonObjects.entrySet()) {
            Object object = entry.getValue();
            // controller类上的 RequestMapping 注解值：/demo
            String baseUrl = "";
            // controller类上的 MySecurity 注解值：["kyrie", "kobe"]
            String[] baseUserNameArr = new String[]{};
            if (object.getClass().isAnnotationPresent(MyController.class)) {
                // 获取@MySecurity注解中配置的用户
                MySecurity mySecurityAnnotation = object.getClass().getAnnotation(MySecurity.class);
                if (mySecurityAnnotation != null && !"".equals(mySecurityAnnotation.value())) {
                    baseUserNameArr = mySecurityAnnotation.value();
                }

                MyRequestMapping annotation = object.getClass().getAnnotation(MyRequestMapping.class);
                if (annotation != null && !"".equals(annotation.value())) {
                    String url = annotation.value();
                    url = url.startsWith("/") ? url : "/" + url;
                    baseUrl = url.endsWith("/") ? url.substring(0, url.lastIndexOf("/")) : url;
                }
                Method[] methods = object.getClass().getMethods();
                for (Method method : methods) {
                    MyRequestMapping myRequestMappingAnnotation = method.getAnnotation(MyRequestMapping.class);
                    if (myRequestMappingAnnotation == null || "".equals(myRequestMappingAnnotation.value())) {
                        continue;
                    }
                    // controller类方法上的RequestMapping注解值：/handle01
                    String methodUrl = myRequestMappingAnnotation.value();
                    methodUrl = methodUrl.startsWith("/") ? methodUrl : "/" + methodUrl;
                    methodUrl = methodUrl.endsWith("/") ? methodUrl.substring(0, methodUrl.lastIndexOf("/")) : methodUrl;
                    String requestMappingUrl = baseUrl + methodUrl;

                    // url 与 method 映射封装类（标记方法参数的位置）
                    HandlerMapping handlerMapping = new HandlerMapping(object, method);
                    Parameter[] parameters = method.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        if (parameter.getType() == HttpServletRequest.class || parameter.getType() == HttpServletResponse.class) {
                            handlerMapping.getParamIndexMapping().put(parameter.getType().getSimpleName(), i);
                        } else {
                            handlerMapping.getParamIndexMapping().put(parameter.getName(), i);
                        }
                    }

                    // controller类RequestMapping方法上的MySecurity注解值：["irving"]
                    handlerMapping.getSecurityUserNameList().addAll(Arrays.asList(baseUserNameArr));
                    MySecurity mySecurityAnnotationMethod = method.getAnnotation(MySecurity.class);
                    if (mySecurityAnnotationMethod != null && !"".equals(mySecurityAnnotationMethod.value())) {
                        String[] userNameArr = mySecurityAnnotationMethod.value();
                        handlerMapping.getSecurityUserNameList().addAll(Arrays.asList(userNameArr));
                    }

                    handlerMappingMap.put(requestMappingUrl, handlerMapping);
                }
            }
        }
    }

    /**
     * 实现依赖注入
     */
    private void doAutowired() {
        if (singletonObjects.isEmpty()) return;

        for (Map.Entry<String, Object> entry : singletonObjects.entrySet()) {
            Object object = entry.getValue();
            Field[] declaredFields = object.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                try {
                    MyAutowired annotation = declaredField.getAnnotation(MyAutowired.class);
                    if (annotation == null) continue;
                    boolean required = annotation.required();
                    String beanName = declaredField.getType().getSimpleName();
                    Object fieldObject = singletonObjects.get(beanName);
                    if (required && fieldObject == null) throw new Exception("required " + beanName + " not find");
                    declaredField.setAccessible(true);
                    declaredField.set(object, fieldObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断类上面是否有注解，进行初始化，放入自定义bean容器中singletonObjects
     * ki.springmvc.demo.controller.DemoController
     */
    private void doInstance() {
        if (classNameList.isEmpty()) return;

        for (String className : classNameList) {
            try {
                Class<?> aClass = Class.forName(className);
                if (aClass.isAnnotationPresent(MyController.class)) {
                    MyController annotation = aClass.getAnnotation(MyController.class);
                    String beanName = annotation.value();
                    Object instance = aClass.newInstance();
                    if ("".equals(beanName.trim())) {
                        beanName = lowerFirstClassName(aClass.getSimpleName());
                    }
                    singletonObjects.put(beanName, instance);
                } else if (aClass.isAnnotationPresent(MyService.class)) {
                    MyService annotation = aClass.getAnnotation(MyService.class);
                    String beanName = annotation.value();
                    Object instance = aClass.newInstance();
                    if ("".equals(beanName.trim())) {
                        beanName = lowerFirstClassName(aClass.getSimpleName());
                    }
                    singletonObjects.put(beanName, instance);

                    Class<?>[] interfaces = aClass.getInterfaces();
                    for (int i = 0; i < interfaces.length; i++) {
                        Class<?> anInterface = interfaces[i];
                        singletonObjects.put(anInterface.getSimpleName(), instance);
                    }
                } else {
                    continue;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 首字母小写方法
     * @param className
     * @return
     */
    private String lowerFirstClassName(String className) {
        char[] chars = className.toCharArray();
        if (chars.length == 0) return "";
        if ('A' <= chars[0] && chars[0] <= 'Z') {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }

    /**
     * 获取目录下的所有全限定类名，放入集合中以便后续遍历初始化
     * @param scanPackage ki.springmvc.demo ===> 磁盘路径/ki/springmvc/demo
     */
    private void doScanForGetClassName(String scanPackage) {
        String scanPackagePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replaceAll("\\.", "/");
        File packageFile = new File(scanPackagePath);

        for (File listFile : packageFile.listFiles()) {
            if (listFile.isDirectory()) {
                doScanForGetClassName(scanPackage + "." + listFile.getName());
            } else if (listFile.getName().endsWith(".class")) {
                classNameList.add(scanPackage + "." + listFile.getName().replaceAll(".class", ""));
            }
        }
    }

    /**
     * 加载配置文件 springmvc.properties
     * @param config
     */
    private void doLoadConfig(ServletConfig config) {
        // 获取web.xml中配置的servlet初始化参数的路径<param-value>classpath:springmvc.properties</param-value>
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        contextConfigLocation = contextConfigLocation.substring(contextConfigLocation.lastIndexOf(":") + 1);
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            this.properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
