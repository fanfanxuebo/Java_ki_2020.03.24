package ki.springmvc.demo.controller;

import ki.springmvc.demo.service.DemoService;
import ki.springmvc.mvcframework.annotation.MyAutowired;
import ki.springmvc.mvcframework.annotation.MyController;
import ki.springmvc.mvcframework.annotation.MyRequestMapping;
import ki.springmvc.mvcframework.annotation.MySecurity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-05 18:44:00 星期日
 */
@MySecurity({"kyrie", "kobe"})
@MyRequestMapping("demo")
@MyController
public class DemoController {

    @MyAutowired
    private DemoService demoService;

    @MySecurity("irving")
    @MyRequestMapping("/handle01")
    public void handle01(HttpServletResponse response, String username) {
        String date = demoService.handle01(username);
        try {
            response.getWriter().write("hello, " + username + " ! your access time is " + date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
