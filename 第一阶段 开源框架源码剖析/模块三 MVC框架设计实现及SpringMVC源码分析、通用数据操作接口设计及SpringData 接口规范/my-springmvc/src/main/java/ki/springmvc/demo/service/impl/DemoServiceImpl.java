package ki.springmvc.demo.service.impl;

import ki.springmvc.demo.service.DemoService;
import ki.springmvc.mvcframework.annotation.MyService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-05 18:46:07 星期日
 */
@MyService
public class DemoServiceImpl implements DemoService {

    @Override
    public String handle01(String username) {
        System.out.println("ki.springmvc.demo.service.impl.DemoServiceImpl.handle01: " + username);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
