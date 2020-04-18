package ki.fanxuebo.server.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-18 12:01:16 星期六
 */
public class Context {

    private String webName;
    private List<Wrapper> wrapperList = new ArrayList<>();

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public List<Wrapper> getWrapperList() {
        return wrapperList;
    }

    public void setWrapperList(List<Wrapper> wrapperList) {
        this.wrapperList = wrapperList;
    }
}
