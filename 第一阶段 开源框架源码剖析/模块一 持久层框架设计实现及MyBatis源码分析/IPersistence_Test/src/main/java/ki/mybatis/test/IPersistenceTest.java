package ki.mybatis.test;

import ki.mybatis.dao.IUserDao;
import ki.mybatis.io.Resources;
import ki.mybatis.pojo.User;
import ki.mybatis.sqlSession.SqlSession;
import ki.mybatis.sqlSession.SqlSessionFactory;
import ki.mybatis.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-03-27 14:25:11 星期五
 */
public class IPersistenceTest {

    private SqlSession sqlSession;

    @Before
    public void getSqlSession() throws Exception {
        // 根据配置文件路径获取字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 通过字节流使用sqlSessionFactory工厂类获取sqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void test () throws Exception {
        List<User> userList = sqlSession.selectAll("ki.mybatis.dao.IUserDao.selectAll");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void getMapperInsertTest() {
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = new User();
        user.setUsername("kyrie");
        user.setPassword("irving");
        user.setBirthday("2020-03-27");
        int rows = userDao.insert(user);
        System.out.println(rows);
    }

    @Test
    public void getMapperUpdateTest() {
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = new User();
        user.setId(2);
        user.setUsername("kyrie");
        user.setPassword("irving");
        user.setBirthday("2020-03-27");
        int rows = userDao.update(user);
        System.out.println(rows);
    }

    @Test
    public void getMapperDeleteTest() {
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = new User();
        user.setUsername("kyrie");
        int rows = userDao.delete(user);
        System.out.println(rows);
    }
}
