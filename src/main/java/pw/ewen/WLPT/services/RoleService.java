package pw.ewen.WLPT.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

import java.util.List;

/**
 * created by wenliang on 20210226
 */
@Service
public class RoleService {

    private RoleRepository roleRepository;
    private UserService userService;

    @Autowired
    public RoleService(RoleRepository roleRepository,
                       UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    /**
     * 找到一个角色
     * @param id    角色id
     * @return  角色
     */
    public Role findOne(String id) {
        return this.roleRepository.findOne(id);
    }

    /**
     * 返回所有角色(不分页）
     * @return
     */
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    /**
     * 返回所有角色（分页）
     * @param pr    分页对象
     * @return  角色
     */
    public Page<Role> findAll(PageRequest pr)  {
        return this.roleRepository.findAll(pr);
    }

    /**
     * 保存
     * @param role
     * @return
     */
    public Role save(Role role) {
        return this.roleRepository.save(role);
    }

    /**
     * 通过id删除角色，如果角色下有用户则抛出异常
     * @param ids
     */
    @Transactional
    public void delete(String[] ids) {
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        String filter = "id()";
//        this.userService.findAll()
    }

}
