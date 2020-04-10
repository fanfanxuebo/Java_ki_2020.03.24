package ki.mybatis.sqlSession;

import ki.mybatis.config.BoundSql;
import ki.mybatis.pojo.Configuration;
import ki.mybatis.pojo.MappedStatement;
import ki.mybatis.utils.GenericTokenParser;
import ki.mybatis.utils.ParameterMapping;
import ki.mybatis.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxuebo
 * @description Mybatis执行器默认实现，负责sql语句的生成和查询缓存的维护
 * @company ki
 * @createDate 2020-03-27 15:27:06 星期五
 */
public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, String statementId, Object... params) throws Exception {
        // 第一，注册驱动，获取链接
        Connection connection = configuration.getDataSource().getConnection();
        // 第二，获取sql语句，并进行转换处理
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        String sql = mappedStatement.getSql();
        BoundSql boundSql = BoundSql(sql);
        // 第三，获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getParseSql());
        // 第四，设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterTypeClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i <parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object object = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, object);
        }
        //第五，执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //第六，封装返回结果集
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);
        ArrayList<Object> resultList = new ArrayList<>();
        while (resultSet.next()) {
            Object object = resultTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i + 1);
                Object value = resultSet.getObject(columnName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(object, value);
            }
            resultList.add(object);
        }
        return (List<E>) resultList;
    }

    @Override
    public int insert(Configuration configuration, String statementId, Object[] params) throws Exception {
        // 第一，注册驱动，获取链接
        Connection connection = configuration.getDataSource().getConnection();
        // 第二，获取sql语句，并进行转换处理
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        String sql = mappedStatement.getSql();
        BoundSql boundSql = BoundSql(sql);
        // 第三，获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getParseSql());
        // 第四，设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterTypeClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i <parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object object = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, object);
        }
        //第五，执行sql
        int rows = preparedStatement.executeUpdate();
        //第六，封装返回结果集
        return rows;
    }

    @Override
    public int update(Configuration configuration, String statementId, Object[] params) throws Exception {
        // 第一，注册驱动，获取链接
        Connection connection = configuration.getDataSource().getConnection();
        // 第二，获取sql语句，并进行转换处理
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        String sql = mappedStatement.getSql();
        BoundSql boundSql = BoundSql(sql);
        // 第三，获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getParseSql());
        // 第四，设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterTypeClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i <parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object object = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, object);
        }
        //第五，执行sql
        int rows = preparedStatement.executeUpdate();
        //第六，封装返回结果集
        return rows;
    }

    @Override
    public int delete(Configuration configuration, String statementId, Object[] params) throws Exception {
        // 第一，注册驱动，获取链接
        Connection connection = configuration.getDataSource().getConnection();
        // 第二，获取sql语句，并进行转换处理
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        String sql = mappedStatement.getSql();
        BoundSql boundSql = BoundSql(sql);
        // 第三，获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getParseSql());
        // 第四，设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterTypeClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i <parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object object = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, object);
        }
        //第五，执行sql
        int rows = preparedStatement.executeUpdate();
        //第六，封装返回结果集
        return rows;
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        } else {
            return null;
        }
    }

    private BoundSql BoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappingList();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappingList);
        return boundSql;
    }
}
