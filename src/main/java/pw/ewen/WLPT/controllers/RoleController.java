package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.RoleDTO;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.exceptions.domain.DeleteRoleException;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.RoleService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    //将role对象转为DTO对象的内部辅助类
    static class RoleDTOConverter implements Converter<Role, RoleDTO> {
        @Override
        public RoleDTO convert(Role source) {
            return  RoleDTO.convertFromRole(source);
        }
    }

    /**
     * 获取全部角色
     * @return
     */
    @RequestMapping(value="/all", method=RequestMethod.GET, produces="application/json")
    public List<Role> getAllRoles(){
        return  this.roleService.findAll();
    }

    /**
     * 获取角色（分页）
     * @param pageIndex 第几页
     * @param pageSize  每页多少条
     * @param sortDirection ASC正序  DESC倒序
     * @param sortField 排序字段名
     * @return 角色数据
     */
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<RoleDTO> getRolesWithPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                          @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
                                          @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                                          @RequestParam(value = "sortField", defaultValue = "name") String sortField,
                                          @RequestParam(value = "filter", defaultValue = "") String filter){
        Page<Role> roles;
        PageRequest pr = new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.fromString(sortDirection), sortField));

        if(filter.isEmpty()){
            roles =  this.roleService.findAll(pr);
        }else{
            SearchSpecificationsBuilder<Role> builder = new SearchSpecificationsBuilder<>();
            roles =  this.roleService.findAll(builder.build(filter), pr);
        }
        return roles.map(new RoleDTOConverter());
    }

    /**
     * 获取一个角色
     * @param roleId 角色Id
     * @return  角色数据，如果找不到返回404
     */
    @RequestMapping(value="/{roleId}", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<?> getOneRole(@PathVariable("roleId") String roleId){
        Role role = roleService.findOne(roleId);
        if(role == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(RoleDTO.convertFromRole(role), HttpStatus.OK);
    }

    /**
     * 检查角色id是否存在
     * @param roleId    角色id
     * @return  true 角色存在 false 角色不存在
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/check/{roleId}")
    public boolean checkRoleExist(String roleId) {
        return roleService.findOne(roleId) != null;
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
     * 删除角色
     * @param roleIds   角色Id
     */
    @RequestMapping(value = "/{roleIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("roleIds") String roleIds){
        String[] arrRoleIds = roleIds.split(",");
        //检查角色下是否有用户，有则不允许删除
        for(String id : arrRoleIds){
            Role role = this.roleService.findOne(id);
            if(role != null ){
                Set<User> users = role.getUsers();
                if(users.isEmpty()){
                    this.roleService.delete(id);
                }else{
                    throw new DeleteRoleException("试图删除角色:"+role.toString()+"失败，因为该角色下有用户");
                }
            }
        }

    }
}
