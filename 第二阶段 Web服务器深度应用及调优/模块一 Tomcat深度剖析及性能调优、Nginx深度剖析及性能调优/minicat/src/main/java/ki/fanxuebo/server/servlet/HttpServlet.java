package ki.fanxuebo.server.servlet;

import ki.fanxuebo.server.util.MyRequest;
import ki.fanxuebo.server.util.MyResponse;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-17 07:57:37 星期五
 */
public abstract class HttpServlet implements Servlet {

    protected void doGet(MyRequest myRequest, MyResponse myResponse) {
        System.out.println("ki.fanxuebo.server.servlet.HttpServlet.doGet");
    }

    protected void doPost(MyRequest myRequest, MyResponse myResponse) {
        System.out.println("ki.fanxuebo.server.servlet.HttpServlet.doPost");
    }

    public void service(MyRequest myRequest, MyResponse myResponse) {
        /*try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        if ("GET".equals(myRequest.getMethod())) {
            doGet(myRequest, myResponse);
        } else if ("POST".equals(myRequest.getMethod())) {
            doPost(myRequest, myResponse);
        } else {
            System.out.println("未识别的请求方式" + myRequest.getMethod());
        }
    }
}
