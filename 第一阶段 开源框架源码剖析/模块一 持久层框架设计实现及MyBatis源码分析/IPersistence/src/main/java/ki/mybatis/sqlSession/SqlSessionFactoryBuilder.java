package ki.mybatis.sqlSession;

import ki.mybatis.config.XMLConfigBuilder;
import ki.mybatis.pojo.Configuration;

import java.io.InputStream;

/**
 * @author fanxuebo
 * @description Builder构建者模式创建sqlSessionFactory工厂对象
 * @company ki
 * @createDate 2020-03-27 15:17:20 星期五
 */
public class SqlSessionFactoryBuilder {

    /**
     * @Author fanxuebo
     * @Date 2020/3/27 16:17
     * @Description 此时需要解析字节流，封装Configuration对象，因为后续sqlSession执行sql语句需要数据源和sql信息
     * 同时每个sqlSession对象只需要创建一次Configuration对象，所以通过带参构造方法创建sqlSession对象，此处是通过sqlSessionFactory工厂创建sqlSession
     **/
    public SqlSessionFactory build(InputStream resourceAsStream) throws Exception {
        // 使用dom4j解析配置文件，封装为Configuration对象
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(resourceAsStream);
        return new DefaultSqlSessionFactory(configuration);
    }
}
