package ki.mybatis.sqlSession;

/**
 * @author fanxuebo
 * @description SqlSessionFactory工厂接口类
 * @company ki
 * @createDate 2020-03-27 15:17:36 星期五
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
