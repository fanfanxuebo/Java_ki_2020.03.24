package ki.mybatis.io;

import java.io.InputStream;

/**
 * @author fanxuebo
 * @description 根据文件路径获取字节输入流
 * @company ki
 * @createDate 2020-03-27 14:28:45 星期五
 */
public class Resources {

    public static InputStream getResourceAsStream(String path) {
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
