package ki.springmvc.sss.dao;

import ki.springmvc.sss.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-08 10:11:16 星期三
 */
public interface ResumeDao extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {

}
