package ki.spring.service.impl;

import ki.spring.annotation.MyAutowired;
import ki.spring.annotation.MyService;
import ki.spring.annotation.MyTransactional;
import ki.spring.dao.AccountDao;
import ki.spring.pojo.Account;
import ki.spring.service.TransferService;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-03 08:05:42 星期五
 */
@MyTransactional
@MyService("transferService")
public class TransferServiceImpl implements TransferService {

    @MyAutowired
    private AccountDao accountDao;

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney()-money);
        to.setMoney(to.getMoney()+money);

        accountDao.updateAccountByCardNo(to);
        int c = 1/0;
        accountDao.updateAccountByCardNo(from);
    }
}
