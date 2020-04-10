package ki.mybatis.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-03-27 17:30:40 星期五
 */
public class ParameterMappingTokenHandler implements TokenHandler{

    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    /**
     * @Author fanxuebo
     * @Date 2020/3/27 17:36
     * @Description content是参数名称 #{id} #{username}
     **/
    @Override
    public String handleToken(String content) {
        parameterMappingList.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
