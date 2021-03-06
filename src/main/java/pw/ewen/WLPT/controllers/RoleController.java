package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.configs.biz.BizConfig;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.RoleDTO;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.dtoconvertors.RoleDTOConvertor;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.exceptions.domain.DeleteRoleException;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.RoleService;
import pw.ewen.WLPT.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    //将role对象转为DTO对象的内部辅助类
    static class RoleDTOConverter implements Converter<Role, RoleDTO> {
        @Override
        public RoleDTO convert(Role source) {
            return  RoleDTO.convertFromRole(source);
        }
    }

    /**
     * @return 获取全部角色(不分页)
     */
    @RequestMapping(value="/all", method=RequestMethod.GET, produces="application/json")
    public List<RoleDTO> getAllRoles(){
        return this.roleService.findAll().stream().map(RoleDTO::convertFromRole).collect(Collectors.toList());
    }

    /**
     * 获取角色（分页）
     * @return 角色数据
     */
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<RoleDTO> getRolesWithPage(PageInfo pageInfo){
        Page<Role> roles;
        PageRequest pr = pageInfo.getPageRequest();

        if(pageInfo.getFilter().isEmpty()){
            roles =  this.roleService.findAll(pr);
        }else{
            roles =  this.roleService.findAll(pageInfo.getFilter(), pr);
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
    public boolean checkRoleExist(@PathVariable("roleId") String roleId) {
        return roleService.findOne(roleId) != null;
    }

    /**
     * 保存角色信息
     * @param roleDTO  角色DTO
     * @return  保存的角色数据
     */
    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public RoleDTO save(@RequestBody RoleDTO roleDTO) {
        Role role = RoleDTO.convertToRole(roleDTO, roleService);
        return RoleDTO.convertFromRole(this.roleService.save(role));
    }

    /**
     * 删除角色
     * @param roleIds   角色Id数字
     */
    @RequestMapping(method=RequestMethod.DELETE, value = "/{roleIds}")
    public void delete(@PathVariable String roleIds){
        String[] arrRoldIds = roleIds.split(",");
        this.roleService.delete(arrRoldIds);
    }

    /**
     * 设置角色的用户
     */
    @RequestMapping(value = "/setusers/{roleId}", method = RequestMethod.PUT)
    public void setUsers(@PathVariable("roleId") String roleId, @RequestBody String[] userIds) {
        Role anonymousRole = roleService.findOne(BizConfig.getAnonymousRoleId());

        Role role = roleService.findOne(roleId);
        if( role != null ) {
            // 清空该角色原本的用户,被清空的用户角色归入anonymous组
            Set<User> users = role.getUsers();
            users.forEach( (user -> {
                user.setRole(anonymousRole);
            }));
            // 将指定用户加入该角色
            for (String userId : userIds ) {
                User user = this.userService.findOne(userId);
                user.setRole(role);
                this.userService.save(user);
            }
        }
        roleService.save(role);
    }
}
