package ki.springmvc.sss.controller;

import ki.springmvc.sss.pojo.Resume;
import ki.springmvc.sss.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-08 15:51:15 星期三
 */
@RequestMapping("resume")
@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @RequestMapping("login")
    public ModelAndView login(ModelAndView modelAndView, HttpServletRequest request, String username, String password) {
        if (!"admin".equals(username) || !"admin".equals(password)) {
            modelAndView.addObject("errorMsg", "用户名或密码错误，请重新登录！");
            modelAndView.setViewName("login");
            return modelAndView;
        }
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("password", password);
        modelAndView.setViewName("redirect:/resume/queryAll");
        return modelAndView;
    }

    @RequestMapping("queryAll")
    public ModelAndView queryAll(ModelAndView modelAndView) {
        List<Resume> resumeList = resumeService.queryAll();
        for (Resume resume : resumeList) {
            System.out.println(resume);
        }
        modelAndView.addObject("resumeList", resumeList);
        modelAndView.setViewName("list");
        return modelAndView;
    }

    @RequestMapping("toSaveOrEdit")
    public ModelAndView toSaveOrEdit(Resume resume, ModelAndView modelAndView) {
        if (resume != null && resume.getId() != null) {
            resume = resumeService.findOne(resume.getId());
        }
        modelAndView.addObject("resume", resume);
        modelAndView.setViewName("saveOrEdit");
        return modelAndView;
    }

    @RequestMapping("saveOrEdit")
    public String saveOrEdit(Resume resume) {
        resumeService.saveAndFlush(resume);
        return "redirect:queryAll";
    }

    @RequestMapping("delete")
    public String delete(Resume resume) {
        resumeService.deleteById(resume.getId());
        return "redirect:queryAll";
    }
}
