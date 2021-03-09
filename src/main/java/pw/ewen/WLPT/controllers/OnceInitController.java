package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.services.*;
import pw.ewen.WLPT.services.resources.MenuService;

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

    @Autowired
    public OnceInitController(MenuService menuService,
                              ResourceTypeService resourceTypeService,
                              ResourceRangeService resourceRangeService,
                              PermissionService permissionService,
                              RoleService roleService,
                              UserService userService) {
        this.resourceTypeService = resourceTypeService;
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.resourceRangeService = resourceRangeService;
    }


    //初始化菜单权限
    //admin角色对所有菜单都有权限
    private void authorizeMenu(){
        Role adminRole = this.roleService.findOne("admin");

        ResourceType menuResourceType = new ResourceType("pw.ewen.WLPT.domains.entities.resources.Menu", "menu", "系统菜单");
        this.resourceTypeService.save(menuResourceType);
        ResourceRange matchAllRange = new ResourceRange("", adminRole, menuResourceType);
        this.resourceRangeService.save(matchAllRange);

        //添加ACL权限,对所有菜单有写权限（写权限包含读权限）
        permissionService.insertPermission(matchAllRange.getId(), BasePermission.WRITE);
    }


    /**
     * 对admin菜单进行授权。
     */
    @RequestMapping(value = "/adminmenuinit", method = RequestMethod.PUT )
    @Transactional
    public void adminMenuInit() {
        this.authorizeMenu();
    }

}
