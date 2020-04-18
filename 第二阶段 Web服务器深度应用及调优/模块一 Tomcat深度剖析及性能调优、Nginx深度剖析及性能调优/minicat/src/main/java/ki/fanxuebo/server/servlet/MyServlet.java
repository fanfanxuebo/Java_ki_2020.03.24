package ki.fanxuebo.server.servlet;

import ki.fanxuebo.server.util.HttpProtocolUtil;
import ki.fanxuebo.server.util.MyRequest;
import ki.fanxuebo.server.util.MyResponse;

import java.io.IOException;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-17 08:07:03 星期五
 */
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(MyRequest myRequest, MyResponse myResponse) {
        String content = "<h1>" + myRequest.getUrl() + " MyServlet doGet</h1>";
        try {
            myResponse.output(HttpProtocolUtil.getHttpHeader200(content.length()) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(MyRequest myRequest, MyResponse myResponse) {
        String content = "<h1>" + myRequest.getUrl() + " MyServlet doPost</h1>";
        try {
            myResponse.output(HttpProtocolUtil.getHttpHeader200(content.length()) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }
}
