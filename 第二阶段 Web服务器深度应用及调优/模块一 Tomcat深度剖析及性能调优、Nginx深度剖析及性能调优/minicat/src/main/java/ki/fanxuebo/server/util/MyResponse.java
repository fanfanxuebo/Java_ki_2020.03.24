package ki.fanxuebo.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-16 20:20:40 星期四
 */
public class MyResponse {

    private OutputStream outputStream;

    public MyResponse() {
    }

    public MyResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

    public void outputHtml(String path) throws IOException {
        String absoluteResourcePath = StaticResourceUtil.getStaticResourcePath(path);
        File file = new File(absoluteResourcePath);
        if (file.exists() && file.isFile()) {
            StaticResourceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        } else {
            output(HttpProtocolUtil.getHttpHeader404());
        }
    }
}
