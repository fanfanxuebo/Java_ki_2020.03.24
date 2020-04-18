package ki.fanxuebo.server.util;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-16 18:37:20 星期四
 */
public class HttpProtocolUtil {

    public static String getHttpHeader200(long contentLength) {
        return "HTTP/1.1 200 OK \n" +
                "Content-Type text/html \n" +
                "Content-Length: " + contentLength + " \n" +
                "\r\n";
    }

    public static String getHttpHeader404() {
        String data = "<h1>404 NOT Found!</h1>";
        return "HTTP/1.1 404 NOT Found \n" +
                "Content-Type text/html \n" +
                "Content-Length: " + data.getBytes().length + " \n" +
                "\r\n" + data;
    }
}
