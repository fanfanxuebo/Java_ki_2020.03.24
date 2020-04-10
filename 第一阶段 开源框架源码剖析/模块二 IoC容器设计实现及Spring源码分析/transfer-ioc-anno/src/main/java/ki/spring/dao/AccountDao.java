package ki.spring.dao;

import ki.spring.pojo.Account;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-03 08:07:52 星期五
 */
public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
