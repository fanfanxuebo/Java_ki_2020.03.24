package ki.spring.utils;

import ki.spring.annotation.MyAutowired;
import ki.spring.annotation.MyComponent;

import java.sql.SQLException;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-03 17:23:25 星期五
 */
@MyComponent
public class TransactionManager {

    @MyAutowired
    private ConnectionUtils connectionUtils;

    // 开启手动事务控制
    public void beginTransaction() throws SQLException {
        System.out.println(connectionUtils);
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
    }

    // 提交事务
    public void commit() throws SQLException {
        connectionUtils.getCurrentThreadConn().commit();
    }

    // 回滚事务
    public void rollback() throws SQLException {
        connectionUtils.getCurrentThreadConn().rollback();
    }
}
