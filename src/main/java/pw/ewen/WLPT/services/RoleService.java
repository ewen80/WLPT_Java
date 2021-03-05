package pw.ewen.WLPT.services;

import org.apache.commons.lang3.StringUtils;
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
     * 返回符合条件的角色
     * @param spec 过滤表达式
     * @return
     */
    public List<Role> findAll(Specification<Role> spec) {
        return this.roleRepository.findAll(spec);
    }

    /**
     * 返回符合条件的角色（分页）
     * @param spec
     * @param pr
     * @return
     */
    public Page<Role> findAll(Specification<Role> spec, PageRequest pr) {
        return this.roleRepository.findAll(spec, pr);
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
     * 通过多个id删除角色，如果角色下有用户则抛出异常， 如果角色有对应权限配置则抛出异常。多个角色有一个发生异常，全部回滚。
     * @param roleIds 角色id数组
     */
    @Transactional
    public void delete(String[] roleIds) throws  DeleteRoleException {
        for (String id: roleIds) {
            this.delete(id);
        }
    }

    /**
     * 通过id删除角色，如果角色下有用户则抛出异常， 如果角色有对应权限配置则抛出异常。
     * @param roleId    角色id
     * @throws DeleteRoleException
     */
    public void delete(String roleId) throws DeleteRoleException {
        //检查角色是否能删除
        if(checkDelete(roleId)) {
            // 该角色下没有用户和权限配置可以删除
            this.roleRepository.delete(roleId);
        } else {
            // 角色下有用户或者权限配置
            throw new DeleteRoleException("删除角色时发生错误，可能是角色下有用户或者有权限配置。");
        }
    }

    // 检查该角色是否能够删除，判断角色下是否有用户和权限配置
    private boolean checkDelete(String roleId) {
        Role role = this.roleRepository.findOne(roleId);
        return (role.getUsers().size() == 0 && role.getResourceRanges().size() == 0);
    }

}
