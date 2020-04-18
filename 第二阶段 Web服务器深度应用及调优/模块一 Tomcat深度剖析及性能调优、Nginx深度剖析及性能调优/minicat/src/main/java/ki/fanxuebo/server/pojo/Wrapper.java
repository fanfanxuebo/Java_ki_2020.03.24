package ki.fanxuebo.server.pojo;

import ki.fanxuebo.server.servlet.HttpServlet;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-18 15:27:29 星期六
 */
public class Wrapper {

    private String url;
    private HttpServlet servlet;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpServlet getServlet() {
        return servlet;
    }

    public void setServlet(HttpServlet servlet) {
        this.servlet = servlet;
    }
}
