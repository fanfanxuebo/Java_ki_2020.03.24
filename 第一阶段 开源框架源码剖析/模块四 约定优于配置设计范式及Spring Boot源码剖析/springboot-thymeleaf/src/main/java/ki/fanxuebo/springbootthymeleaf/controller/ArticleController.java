package ki.fanxuebo.springbootthymeleaf.controller;

import ki.fanxuebo.springbootthymeleaf.pojo.Article;
import ki.fanxuebo.springbootthymeleaf.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-15 15:13:23 星期三
 */
@RequestMapping("article")
@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("index")
    public ModelAndView index(ModelAndView modelAndView, Integer pageNo) {
        pageNo = pageNo == null || pageNo < 0 ? 0 : pageNo;
        Page<Article> page = articleService.findByPage(pageNo);
        modelAndView.addObject("page", page);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
