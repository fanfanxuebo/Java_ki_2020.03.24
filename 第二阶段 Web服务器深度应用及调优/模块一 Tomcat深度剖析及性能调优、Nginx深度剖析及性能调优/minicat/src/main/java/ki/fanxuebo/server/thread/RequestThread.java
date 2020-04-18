package ki.fanxuebo.server.thread;

import ki.fanxuebo.server.pojo.Context;
import ki.fanxuebo.server.pojo.Host;
import ki.fanxuebo.server.pojo.Mapper;
import ki.fanxuebo.server.pojo.Wrapper;
import ki.fanxuebo.server.servlet.HttpServlet;
import ki.fanxuebo.server.util.MyRequest;
import ki.fanxuebo.server.util.MyResponse;

import java.io.InputStream;
import java.net.Socket;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-17 08:58:34 星期五
 */
public class RequestThread extends Thread {

    private Socket socket;
    private Mapper mapper;

    public RequestThread(Socket socket, Mapper mapper) {
        this.socket = socket;
        this.mapper = mapper;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();

            MyRequest myRequest = new MyRequest(inputStream);
            MyResponse myResponse = new MyResponse(socket.getOutputStream());

            for (Host host : mapper.getHostList()) {
                if (myRequest.getUrl().contains(host.getIp())) {
                    for (Context context : host.getContextList()) {
                        if (myRequest.getUrl().contains(context.getWebName())) {
                            for (Wrapper wrapper : context.getWrapperList()) {
                                HttpServlet servlet = wrapper.getServlet();
                                servlet.service(myRequest, myResponse);
                            }
                        }
                    }
                }
            }
            socket.close();
        } catch (Exception e) {

        }
    }
}
