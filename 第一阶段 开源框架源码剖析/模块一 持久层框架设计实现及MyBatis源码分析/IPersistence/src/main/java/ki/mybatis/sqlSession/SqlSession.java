package ki.mybatis.sqlSession;

import java.util.List;

/**
 * @author fanxuebo
 * @description SqlSession接口类
 * @company ki
 * @createDate 2020-03-27 15:18:03 星期五
 */
public interface SqlSession {

    <E> List<E> selectAll(String statementId, Object... params) throws Exception;

    <T> T getMapper(Class<?> mapperClass);

    int insert(String statementId, Object... params) throws Exception;

    int update(String statementId, Object... params) throws Exception;

    int delete(String statementId, Object... params) throws Exception;
}
