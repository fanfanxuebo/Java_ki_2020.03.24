package ki.mybatis.sqlSession;

import ki.mybatis.pojo.Configuration;

/**
 * @author fanxuebo
 * @description 默认SqlSessionFactory工厂对象
 * @company ki
 * @createDate 2020-03-27 15:19:14 星期五
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
