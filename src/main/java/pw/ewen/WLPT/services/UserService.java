package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecification;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

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

    public Page<User> findAll(String filter, PageRequest pr) {
        // 默认过滤已删除用户
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        String filterStr = "deleted:false," + filter;
        return  this.userRepository.findAll(builder.build(filterStr), pr);
    }

    /**
     * 返回翻页格式用户列表
     * @param pr    分页对象
     */
    public Page<User> findAll(PageRequest pr){
        // 默认过滤已删除用户
        Specification<User> spec = (root, query, cb) -> cb.isFalse(root.get("deleted"));
        return this.userRepository.findAll(spec, pr);
    }

    /**
     * 返回所有用户，没有过滤条件
     */
    public List<User> findAll() {
        // 默认过滤已删除用户
        Specification<User> spec = (root, query, cb) -> cb.isFalse(root.get("deleted"));
        return this.userRepository.findAll(spec);
    }

    public List<User> findAll(String filter) {
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        String filterStr = "deleted:false," + filter;
        // 默认过滤已删除用户
        return this.userRepository.findAll(builder.build(filterStr));
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
     * @return 删除的行数
     */
    public int delete(List<String> userIds){
        return this.userRepository.softdelete(userIds);
    }

}
