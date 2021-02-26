package pw.ewen.WLPT.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.RoleRepository;

import java.util.List;

/**
 * created by wenliang on 20210226
 */
@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
    public void delete(String[] ids) {

    }
}
