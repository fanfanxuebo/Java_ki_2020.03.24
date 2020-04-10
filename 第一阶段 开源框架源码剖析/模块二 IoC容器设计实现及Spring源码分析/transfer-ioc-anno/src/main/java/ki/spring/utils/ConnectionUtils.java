package ki.spring.utils;

import ki.spring.annotation.MyAutowired;
import ki.spring.annotation.MyComponent;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-03 08:10:15 星期五
 */
@MyComponent
public class ConnectionUtils {

    @MyAutowired
    private DataSource dataSource;

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public Connection getCurrentThreadConn() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }
}
