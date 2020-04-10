package ki.spring.utils;

import ki.spring.annotation.MyAutowired;
import ki.spring.annotation.MyComponent;
import ki.spring.annotation.MyTransactional;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-03 12:03:03 星期五
 */
@MyComponent
public class MyProxyFactory {

    @MyAutowired
    private TransactionManager transactionManager;

    public Object getCglibProxy(Object object) {
        System.out.println("ki.spring.utils.MyProxyFactory.getCglibProxy");
        return Enhancer.create(object.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                // 判断是否添加了 MyTransactional 注解，即是否开启了事务
                MyTransactional annotation = object.getClass().getAnnotation(MyTransactional.class);
                System.out.println(annotation);
                Object result = null;
                if (annotation != null) {
                    try {
                        transactionManager.beginTransaction();
                        result = method.invoke(object, objects);
                        transactionManager.commit();
                    } catch (Exception e) {
                        transactionManager.rollback();
                    }
                } else {
                    result = method.invoke(object, objects);
                }
                return result;
            }
        });
    }

    public Object getJdkProxy(Object object) {
        System.out.println("ki.spring.utils.MyProxyFactory.getJdkProxy");
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("java.lang.reflect.InvocationHandler.invoke");
                // 判断是否添加了 MyTransactional 注解，即是否开启了事务
                MyTransactional annotation = object.getClass().getAnnotation(MyTransactional.class);
                Object result = null;
                if (annotation != null) {
                    try {
                        System.out.println(transactionManager);
                        transactionManager.beginTransaction();
                        result = method.invoke(object, args);
                        transactionManager.commit();
                    } catch (Exception e) {
                        transactionManager.rollback();
                        throw e;
                    }
                } else {
                    result = method.invoke(object, args);
                }
                return result;
            }
        });
    }
}
