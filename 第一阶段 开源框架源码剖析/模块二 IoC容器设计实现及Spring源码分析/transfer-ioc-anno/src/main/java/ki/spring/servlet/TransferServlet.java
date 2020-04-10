package ki.spring.servlet;

import ki.spring.annotation.MyAutowired;
import ki.spring.annotation.MyComponent;
import ki.spring.annotation.MyRepository;
import ki.spring.annotation.MyService;
import ki.spring.pojo.Result;
import ki.spring.service.TransferService;
import ki.spring.utils.JsonUtils;
import ki.spring.utils.MyProxyFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-03 07:59:27 星期五
 */
@WebServlet(name="transferServlet",urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {

    @MyAutowired
    private TransferService transferService;

    @Override
    public void init() throws ServletException {
        System.out.println("ki.spring.servlet.TransferServlet.init");
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        loadAnnotation(webApplicationContext);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ki.spring.servlet.TransferServlet.doPost");
        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {
            System.out.println("transferService.transfer(fromCardNo,toCardNo,money);");
            transferService.transfer(fromCardNo,toCardNo,money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMessage(e.toString());
        }

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));
    }

    private void loadAnnotation(WebApplicationContext webApplicationContext) {
        DataSource dataSource = webApplicationContext.getBean(DataSource.class);
        Map<String, Object> myComponentMap = webApplicationContext.getBeansWithAnnotation(MyComponent.class);
        Map<String, Object> myRepositoryMap = webApplicationContext.getBeansWithAnnotation(MyRepository.class);
        Map<String, Object> myServiceMap = webApplicationContext.getBeansWithAnnotation(MyService.class);

        log("给 MyProxyFactory 和 ConnectionUtils 和 TransactionManager 类的属性赋值");
        for (Map.Entry<String, Object> entry : myComponentMap.entrySet()) {
            Object value = entry.getValue();
            Field[] declaredFields = value.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                // 如果属性上有自定义的MyAutowired注解，就进行赋值操作
                MyAutowired annotation = declaredField.getAnnotation(MyAutowired.class);
                if (annotation != null) {
                    try {
                        declaredField.setAccessible(true);
                        declaredField.set(value, "dataSource".equals(declaredField.getName()) ? dataSource : myComponentMap.get(declaredField.getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        log("给 AccountDaoImpl 类的属性赋值");
        for (Map.Entry<String, Object> entry : myRepositoryMap.entrySet()) {
            Object value = entry.getValue();
            Field[] declaredFields = value.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                MyAutowired annotation = declaredField.getAnnotation(MyAutowired.class);
                if (annotation != null) {
                    try {
                        declaredField.setAccessible(true);
                        declaredField.set(value, myComponentMap.get(declaredField.getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        log("给 TransferServiceImpl 类的属性赋值");
        for (Map.Entry<String, Object> entry : myServiceMap.entrySet()) {
            Object value = entry.getValue();
            try {
                Field[] declaredFields = value.getClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    MyAutowired annotation = declaredField.getAnnotation(MyAutowired.class);
                    if (annotation != null) {
                        declaredField.setAccessible(true);
                        declaredField.set(value, myRepositoryMap.get(declaredField.getName()));
                    }
                }
                // 获取 TransferServiceImpl 的接口，判断使用JDK还是Cglib生成代理类
                Class<?>[] interfaces = value.getClass().getInterfaces();
                MyProxyFactory myProxyFactory = (MyProxyFactory) myComponentMap.get("myProxyFactory");
                if (interfaces == null || interfaces.length == 0) {
                    Object cglibProxy = myProxyFactory.getCglibProxy(value);
                    myServiceMap.put(entry.getKey(), cglibProxy);
                } else {
                    Object jdkProxy = myProxyFactory.getJdkProxy(value);
                    myServiceMap.put(entry.getKey(), jdkProxy);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log("给 TransferServlet 类的属性赋值");
        Field[] declaredFields1 = this.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields1) {
            MyAutowired annotation = declaredField.getAnnotation(MyAutowired.class);
            if (annotation != null) {
                try {
                    declaredField.setAccessible(true);
                    declaredField.set(this, myServiceMap.get(declaredField.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
