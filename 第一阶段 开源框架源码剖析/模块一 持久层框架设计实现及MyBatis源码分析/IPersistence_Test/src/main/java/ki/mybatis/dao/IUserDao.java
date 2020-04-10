package ki.mybatis.dao;

import ki.mybatis.pojo.User;

import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-03-27 18:27:17 星期五
 */
public interface IUserDao {

    <E> List<E> selectAll();

    int insert(User user);

    int update(User user);

    int delete(User user);
}
