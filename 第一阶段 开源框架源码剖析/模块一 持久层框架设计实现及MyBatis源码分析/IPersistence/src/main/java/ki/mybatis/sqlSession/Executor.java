package ki.mybatis.sqlSession;

import ki.mybatis.pojo.Configuration;

import java.util.List;

/**
 * @author fanxuebo
 * @description Mybatis执行器接口类
 * @company ki
 * @createDate 2020-03-27 15:27:25 星期五
 */
public interface Executor {
    <E> List<E> query(Configuration configuration, String statementId, Object... params) throws Exception;

    int insert(Configuration configuration, String statementId, Object[] params) throws Exception;

    int delete(Configuration configuration, String statementId, Object[] params) throws Exception;

    int update(Configuration configuration, String statementId, Object[] params) throws Exception;
}
