package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.UserRepository;

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
     * @param spec
     * @param pr
     * @return
     */
    public Page<User> findAll(Specification<User> spec, PageRequest pr){
        return this.userRepository.findAll(spec, pr);
    }

    /**
     * 返回用户列表
     * @param spec
     * @return
     */
    public List<User> findAll(Specifications<User> spec){
        return this.userRepository.findAll(spec);
    }
}
