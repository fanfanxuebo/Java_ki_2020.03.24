package ki.fanxuebo.springbootthymeleaf.service;

import ki.fanxuebo.springbootthymeleaf.pojo.Article;
import org.springframework.data.domain.Page;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-15 15:15:00 星期三
 */
public interface ArticleService {

    Page<Article> findByPage(Integer pageNo);
}
