package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.services.*;
import pw.ewen.WLPT.services.resources.MenuService;

import java.util.List;

/**
 * 系统首次运行初始化.
 * created by wenliang on 20210226
 */
@RestController
public class OnceInitController {

    private ResourceTypeService resourceTypeService;
    private PermissionService permissionService;
    private RoleService roleService;
    private ResourceRangeService resourceRangeService;
    private MenuService menuService;

    @Autowired
    public OnceInitController(ResourceTypeService resourceTypeService,
                              ResourceRangeService resourceRangeService,
                              PermissionService permissionService,
                              MenuService menuService,
                              RoleService roleService) {
        this.resourceTypeService = resourceTypeService;
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.resourceRangeService = resourceRangeService;
        this.menuService = menuService;
    }


    //初始化菜单权限
    //admin角色对所有菜单都有权限
    private void authorizeMenu(Role role){
        ResourceType menuResourceType = new ResourceType("pw.ewen.WLPT.domains.entities.resources.Menu", "menu", "系统菜单");
        this.resourceTypeService.save(menuResourceType);

        ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(menuResourceType.getClassName(), role.getId());
        if(range == null) {
            range = new ResourceRange("", role, menuResourceType);
            this.resourceRangeService.save(range);
        }

        //添加ACL权限,对所有菜单有权限
        ResourceRangePermissionWrapper wrapper = permissionService.getByResourceRange(range.getId());
        if(wrapper == null || !wrapper.getPermissions().contains(BasePermission.ADMINISTRATION)) {
            permissionService.insertPermission(range.getId(), BasePermission.ADMINISTRATION);
        }
    }


    /**
     * 对admin菜单进行授权。
     */
    @RequestMapping(value = "/adminmenuinit", method = RequestMethod.PUT, produces = "application/json" )
    @Transactional
    public List<Menu> adminMenuInit() {
        Role adminRole = this.roleService.findOne("admin");
        this.authorizeMenu(adminRole);
        return this.menuService.findPermissionMenuTree(adminRole);
    }

}
