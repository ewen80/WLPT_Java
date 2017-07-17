package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.UserRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by wenliang on 17-4-14.
 */
@Service
public class UserService {

    private UserRepository userRepository;

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
     * 返回一个用户
     * @param id userId
     * @return 如果没有找到返回null
     */
    public User findOne(String id){
        return this.userRepository.findByuserId(id);
    }

    public User save(User user){
        return this.userRepository.save(user);
    }


    /**
     * 软删除
     * @param ids
     */
    public void delete(Collection<Long> ids){
        List<User> users = this.userRepository.findAll(ids);
        for(User user: users){
            user.setDeleted(true);
        }
        this.userRepository.save(users);
    }
}
