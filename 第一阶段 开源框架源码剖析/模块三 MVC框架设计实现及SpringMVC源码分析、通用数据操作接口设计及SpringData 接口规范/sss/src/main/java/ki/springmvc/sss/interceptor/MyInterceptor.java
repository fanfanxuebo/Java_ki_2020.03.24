package ki.springmvc.sss.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-08 16:14:15 星期三
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("ki.springmvc.sss.interceptor.MyInterceptor.preHandle");
        if (!request.getRequestURI().endsWith("login")) {
            String username = (String) request.getSession().getAttribute("username");
            String password = (String) request.getSession().getAttribute("password");
            if (!"admin".equals(username) || !"admin".equals(password)) {
                response.sendRedirect("login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("ki.springmvc.sss.interceptor.MyInterceptor.postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("ki.springmvc.sss.interceptor.MyInterceptor.afterCompletion");
    }
}
