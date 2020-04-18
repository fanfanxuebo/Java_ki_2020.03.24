package ki.fanxuebo.server.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-18 11:39:52 星期六
 */
public class Host {

    private String ip;
    private List<Context> contextList = new ArrayList<>();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<Context> getContextList() {
        return contextList;
    }

    public void setContextList(List<Context> contextList) {
        this.contextList = contextList;
    }
}
