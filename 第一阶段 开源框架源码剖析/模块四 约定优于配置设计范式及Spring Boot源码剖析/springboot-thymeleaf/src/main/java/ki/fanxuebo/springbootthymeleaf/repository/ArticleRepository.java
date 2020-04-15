package ki.fanxuebo.springbootthymeleaf.repository;

import ki.fanxuebo.springbootthymeleaf.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fanxuebo
 * @description
 * @company
 * @createDate 2020-04-15 12:53:59 星期三
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
