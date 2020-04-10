package ki.mybatis.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import ki.mybatis.io.Resources;
import ki.mybatis.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author fanxuebo
 * @description 解析sqlMapConfig配置文件，封装到Configuration对象
 * @company ki
 * @createDate 2020-03-27 14:56:33 星期五
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parseConfig(InputStream inputStream) throws Exception {
        // 使用dom4j将字节流转换为Document对象
        Document document = new SAXReader().read(inputStream);
        // 获取根节点<configuration>
        Element rootElement = document.getRootElement();
        // 获取节点<property>属性信息，封装数据源对象
        List<Element> propertyList = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : propertyList) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        // 将数据源对象封装到Configuration对象中
        configuration.setDataSource(comboPooledDataSource);

        // 继续解析mapper.xml，获取sql信息并进行封装
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            // 获取mapper文件源路径，获取字节流
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            // 继续将mapper信息封装到Configuration中
            new XMLMapperBuilder(configuration).parse(resourceAsStream);
        }


        return configuration;
    }
}
