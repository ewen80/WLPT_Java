package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.UserRepository;

import java.util.List;

/**
 * Created by wenliang on 17-4-14.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 返回翻页格式用户列表
     * @param spec 过滤表达式
     * @param pr    分页对象
     * @return
     */
    public Page<User> findAll(Specification<User> spec, PageRequest pr){
        return this.userRepository.findAll(spec, pr);
    }

    /**
     * 返回翻页格式用户列表
     * @param pr    分页对象
     * @return
     */
    public Page<User> findAll(PageRequest pr){
        return this.userRepository.findAll(pr);
    }

    /**
     * 返回所有用户，没有过滤条件
     * @return
     */
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public List<User> findAll(Specification<User> spec) {
        return this.userRepository.findAll(spec);
    }

    /**
     * 返回一个用户
     * @param id 用户id
     * @return 如果没有找到返回null
     */
    public User findOne(String id){
        return this.userRepository.findOne(id);
    }

    public User save(User user){
        return this.userRepository.save(user);
    }

    /**
     * 删除用户
     * @param userIds 用户id数组
     * @return
     */
    public int delete(List<String> userIds){
        return this.userRepository.softdelete(userIds);
    }

}
