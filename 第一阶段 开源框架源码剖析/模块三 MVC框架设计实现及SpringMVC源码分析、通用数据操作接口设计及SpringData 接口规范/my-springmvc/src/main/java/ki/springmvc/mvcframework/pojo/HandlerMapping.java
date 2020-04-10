package ki.springmvc.mvcframework.pojo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-06 09:31:49 星期一
 */
public class HandlerMapping {

    private Object controller;

    private Method method;

    private Map<String, Integer> paramIndexMapping;

    // 封装可访问的用户名 @MySecurity
    private List<String> securityUserNameList;

    public HandlerMapping(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        paramIndexMapping = new HashMap<>();
        securityUserNameList = new ArrayList<>();
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Map<String, Integer> getParamIndexMapping() {
        return paramIndexMapping;
    }

    public void setParamIndexMapping(Map<String, Integer> paramIndexMapping) {
        this.paramIndexMapping = paramIndexMapping;
    }

    public List<String> getSecurityUserNameList() {
        return securityUserNameList;
    }

    public void setSecurityUserNameList(List<String> securityUserNameList) {
        this.securityUserNameList = securityUserNameList;
    }
}
