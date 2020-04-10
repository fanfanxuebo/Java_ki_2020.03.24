package ki.mybatis.config;

import ki.mybatis.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-03-27 17:29:57 星期五
 */
public class BoundSql {

    private String parseSql;
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public BoundSql(String parseSql, List<ParameterMapping> parameterMappingList) {
        this.parseSql = parseSql;
        this.parameterMappingList = parameterMappingList;
    }

    public String getParseSql() {
        return parseSql;
    }

    public void setParseSql(String parseSql) {
        this.parseSql = parseSql;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
