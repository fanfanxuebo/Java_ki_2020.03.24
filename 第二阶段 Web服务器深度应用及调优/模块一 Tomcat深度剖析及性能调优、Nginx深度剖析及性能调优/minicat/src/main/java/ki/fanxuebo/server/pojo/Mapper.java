package ki.fanxuebo.server.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-18 14:32:03 星期六
 */
public class Mapper {

    private int port;
    private List<Host> hostList = new ArrayList<>();

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }
}
