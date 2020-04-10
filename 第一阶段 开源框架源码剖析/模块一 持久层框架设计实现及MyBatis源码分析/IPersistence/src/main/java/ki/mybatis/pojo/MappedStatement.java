package ki.mybatis.pojo;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-03-27 15:00:44 星期五
 */
public class MappedStatement {

    private String id;
    private String resultType;
    private String parameterType;
    private String sql;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
