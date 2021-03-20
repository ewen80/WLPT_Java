package pw.ewen.WLPT.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.exceptions.domain.DeleteRoleException;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

import java.util.List;

/**
 * created by wenliang on 20210226
 */
@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * 找到一个角色
     * @param id    角色id
     * @return  如果没有找到返回null
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

    public Page<Role> findAll(Specification<Role> spec, PageRequest pr) {
        return this.roleRepository.findAll(spec, pr);
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
     * 通过角色ids删除角色，如果角色下有用户和权限配置则抛出异常
     * @param roleIds   角色id数组
     */
    public void delete(String[] roleIds) throws DeleteRoleException {
        for(String id : roleIds) {
            this.delete(id);
        }
    }

    /**
     * 通过角色id删除角色，如果角色下有用户和权限配置则抛出异常
     * @param roleId    角色id
     * @throws DeleteRoleException
     */
    public  void delete(String roleId) throws DeleteRoleException {
        if(checkCanDelete(roleId)) {
            this.roleRepository.delete(roleId);
        } else {
            throw new DeleteRoleException("删除角色失败，该角色可能还有用户或者权限配置。");
        }
    }

    // 检查角色下面是否有用户和权限配置
    private boolean checkCanDelete(String roleId) {
        Role role = this.roleRepository.findOne(roleId);
        return role.getUsers().size() == 0 && role.getResourceRanges().size() == 0;
    }

}
