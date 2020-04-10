package ki.mybatis.sqlSession;

import ki.mybatis.pojo.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author fanxuebo
 * @description SqlSession默认实现类，封装CRUD方法
 * @company ki
 * @createDate 2020-03-27 15:26:17 星期五
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectAll(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<E> list = simpleExecutor.query(configuration, statementId, params);
        return list;
    }

    @Override
    public int insert(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        int rows = simpleExecutor.insert(configuration, statementId, params);
        return rows;
    }

    @Override
    public int update(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        int rows = simpleExecutor.update(configuration, statementId, params);
        return rows;
    }

    @Override
    public int delete(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        int rows = simpleExecutor.delete(configuration, statementId, params);
        return rows;
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String className = method.getDeclaringClass().getName();
                String methodName = method.getName();
                String statementId = className + "." + methodName;
                switch (methodName) {
                    case "selectAll":
                        return selectAll(statementId, args);
                    case "insert":
                        return insert(statementId, args);
                    case "update":
                        return update(statementId, args);
                    case "delete":
                        return delete(statementId, args);
                }
                return null;
            }
        });
        return (T) proxyInstance;
    }
}
