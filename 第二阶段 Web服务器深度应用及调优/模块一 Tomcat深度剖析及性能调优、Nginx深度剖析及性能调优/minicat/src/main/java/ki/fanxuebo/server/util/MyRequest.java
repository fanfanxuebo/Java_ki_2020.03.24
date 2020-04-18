package ki.fanxuebo.server.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-16 20:13:28 星期四
 */
public class MyRequest {

    private String method;
    private String url;

    public MyRequest(InputStream inputStream) throws IOException {
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);

        String requestStr = new String(bytes);
        String firstLineStr = requestStr.split("\\n")[0];
        System.out.println("读取到request请求字节流中的头信息： " + firstLineStr);
        this.method = firstLineStr.split(" ")[0];
        this.url = "http://localhost:8080" + firstLineStr.split(" ")[1];
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
