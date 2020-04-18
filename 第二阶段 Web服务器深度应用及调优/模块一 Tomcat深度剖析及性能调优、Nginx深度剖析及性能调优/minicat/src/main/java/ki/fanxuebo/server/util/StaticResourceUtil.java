package ki.fanxuebo.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-16 20:35:40 星期四
 */
public class StaticResourceUtil {

    public static String getStaticResourcePath(String path) {
        String absolutePath = StaticResourceUtil.class.getResource("/").getPath();
        return absolutePath.replaceAll("\\\\", "/") + path;
    }

    public static void outputStaticResource(InputStream inputStream, OutputStream outputStream) throws IOException {
        int resourceSize = 0;
        while (resourceSize == 0) {
            resourceSize = inputStream.available();
        }
        outputStream.write(HttpProtocolUtil.getHttpHeader200(resourceSize).getBytes());

        long written = 0L;
        int byteSize = 1024;
        byte[] bytes = new byte[byteSize];
        while (written < resourceSize) {
            if (written + byteSize < resourceSize) {
                byteSize = (int) (resourceSize - written);
                bytes = new byte[byteSize];
            }

            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
            written += byteSize;
        }

    }
}
