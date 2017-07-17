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
import pw.ewen.WLPT.services.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;
    /**
     * 获取全部角色
     * @return
     */
    @RequestMapping(value="/all", method=RequestMethod.GET, produces="application/json")
    public List<Role> getAllRoles(){
        return  this.roleService.getAllRoles();
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
        return this.roleService.getRolesWithPage(pageIndex, pageSize);
    }

    /**
     * 获取一个角色
     * @param roleId 角色Id
     * @return  角色数据
     */
    @RequestMapping(value="/{roleId}", method=RequestMethod.GET, produces="application/json")
    public Role getOneRole(@PathVariable("roleId") String roleId){
        return this.roleService.getOneRole(roleId);
    }

    /**
     * 保存角色信息
     * @param role  角色数据
     * @return  保存的角色数据
     */
    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public Role save(@RequestBody Role role){
        return this.roleService.save(role);
    }

    /**
     * 删除角色,如果有一个角色下有用户则整批角色不允许删除
     * @param ids   角色Ids
     */
    @RequestMapping(value = "/{ids}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("ids") String ids){
        String[] arrIds = ids.split(",");
        ArrayList<Long> longIds = new ArrayList<>();
        for(String id: arrIds){
            longIds.add(Long.valueOf(id));
        }
        this.roleService.delete(longIds);

    }
}
