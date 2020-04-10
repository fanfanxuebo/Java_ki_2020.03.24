package ki.springmvc.sss.service;

import ki.springmvc.sss.pojo.Resume;

import java.util.List;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-08 15:49:43 星期三
 */
public interface ResumeService {

    List<Resume> queryAll();

    Resume saveAndFlush(Resume resume);

    Resume findOne(Long id);

    void deleteById(Long id);
}
