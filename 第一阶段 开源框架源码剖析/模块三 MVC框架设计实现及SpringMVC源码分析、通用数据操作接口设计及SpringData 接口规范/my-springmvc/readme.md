请拷贝项目到其他路径下，该路径包含中文，扫描包时会出错！



@MySecurity注解实现思路：

1、ki.springmvc.mvcframework.pojo.HandlerMapping 在改类中封装可访问用户名集合 securityUserNameList

2、ki.springmvc.mvcframework.servlet.MyDispatcherServlet#initHandlerMapping 在方法中获取Controller类上和方法上的 MySecurity 注解值 ，封装到 securityUserNameList 中

3、ki.springmvc.mvcframework.servlet.MyDispatcherServlet#doPost 每次请求访问时，判断  securityUserNameList 中是否包含，获取request中的username字段值