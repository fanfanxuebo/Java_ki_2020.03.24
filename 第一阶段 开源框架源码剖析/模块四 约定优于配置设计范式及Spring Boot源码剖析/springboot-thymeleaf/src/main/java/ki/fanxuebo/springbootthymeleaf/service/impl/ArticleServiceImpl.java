package ki.fanxuebo.springbootthymeleaf.service.impl;

import ki.fanxuebo.springbootthymeleaf.pojo.Article;
import ki.fanxuebo.springbootthymeleaf.repository.ArticleRepository;
import ki.fanxuebo.springbootthymeleaf.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-15 15:15:13 星期三
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Page<Article> findByPage(Integer pageNo) {
        return articleRepository.findAll(PageRequest.of(pageNo, 2));
    }
}
