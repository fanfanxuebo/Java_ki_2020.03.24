package ki.spring.service;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-03 08:05:08 星期五
 */
public interface TransferService {

    void transfer(String fromCardNo,String toCardNo,int money) throws Exception;
}
