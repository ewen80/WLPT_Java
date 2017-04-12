package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.exceptions.domain.DeleteHaveUsersRoleException;
import pw.ewen.WLPT.repositories.RoleRepository;

import java.util.List;
import java.util.Set;

/**
 * TODO:移植逻辑到services层
 */
@RestController
@RequestMapping(value = "/roles")
public class RoleController {
    private RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * 获取全部角色
     * @return
     */
    @RequestMapping(value="/all", method=RequestMethod.GET, produces="application/json")
    public List<Role> getAllRoles(){
        return  this.roleRepository.findAll();
    }

    /**
     * 获取角色（分页）
     * @param pageIndex 第几页
     * @param pageSize  每页多少条
     * @return 角色数据
     */
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<Role> getRolesWithPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                       @RequestParam(value = "pageSize", defaultValue = "20") int pageSize){
        return roleRepository.findAll(new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
    }

    /**
     * 获取一个角色
     * @param roleId 角色Id
     * @return  角色数据
     */
    @RequestMapping(value="/{roleId}", method=RequestMethod.GET, produces="application/json")
    public Role getOneRole(@PathVariable("roleId") String roleId){
        return roleRepository.findOne(roleId);
    }

//    @RequestMapping(value = "/byname/{name}", method=RequestMethod.GET, produces = "application/json")
//    public List<Role> getRolesByName(@PathVariable("name") String name){
//        return roleRepository.findByName(name);
//    }

    /**
     * 保存角色信息
     * @param role  角色数据
     * @return  保存的角色数据
     */
    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public Role save(@RequestBody Role role){
        return this.roleRepository.save(role);
    }

    /**
     * 删除角色
     * @param roleIds   角色Id
     */
    @RequestMapping(value = "/{roleIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("roleIds") String roleIds){
        String[] arrRoleIds = roleIds.split(",");
        //检查角色下是否有用户，有则不允许删除
        for(String id : arrRoleIds){
            Role role = this.roleRepository.findOne(id);
            if(role != null ){
                Set<User> users = role.getUsers();
                if(users.isEmpty()){
                    this.roleRepository.delete(id);
                }else{
                    throw new DeleteHaveUsersRoleException("试图删除角色:"+role.toString()+"失败，因为该角色下有用户");
                }
            }
        }

    }
}
