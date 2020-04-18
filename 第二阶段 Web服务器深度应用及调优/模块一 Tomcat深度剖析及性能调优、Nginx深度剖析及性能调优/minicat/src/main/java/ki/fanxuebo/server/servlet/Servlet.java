package ki.fanxuebo.server.servlet;

import ki.fanxuebo.server.util.MyRequest;
import ki.fanxuebo.server.util.MyResponse;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-17 07:56:37 星期五
 */
public interface Servlet {

    void init() throws Exception;
    void service(MyRequest myRequest, MyResponse myResponse) throws Exception;
    void destroy() throws Exception;
}
