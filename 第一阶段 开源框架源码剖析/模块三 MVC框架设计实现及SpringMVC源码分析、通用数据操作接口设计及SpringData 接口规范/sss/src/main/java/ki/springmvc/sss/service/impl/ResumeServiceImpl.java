package ki.springmvc.sss.service.impl;

import ki.springmvc.sss.dao.ResumeDao;
import ki.springmvc.sss.pojo.Resume;
import ki.springmvc.sss.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-08 15:50:26 星期三
 */
@Transactional
@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeDao resumeDao;

    @Override
    public List<Resume> queryAll() {
        return resumeDao.findAll();
    }

    @Override
    public Resume saveAndFlush(Resume resume) {
        return resumeDao.saveAndFlush(resume);
    }

    @Override
    public Resume findOne(Long id) {
        return resumeDao.findOne(new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("id"), id);
            }
        }).get();
    }

    @Override
    public void deleteById(Long id) {
        resumeDao.deleteById(id);
    }
}
