package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
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
 * 系统首次运行初始化，一旦正式上线运行不可再调用此方法，否则菜单、账号等数据会被初始化
 * created by wenliang on 20210226
 */
@RestController
public class OnceInitController {

    private MenuService menuService;
    private ResourceTypeService resourceTypeService;
    private PermissionService permissionService;
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public OnceInitController(MenuService menuService,
                              ResourceTypeService resourceTypeService,
                              PermissionService permissionService,
                              RoleService roleService,
                              UserService userService) {
        this.menuService = menuService;
        this.resourceTypeService = resourceTypeService;
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.userService = userService;
    }

    //初始化菜单数据
    private void initialMenu() {
        Menu homeMenu = new Menu();
        homeMenu.setName("Home");
        homeMenu.setPath("/");
        this.menuService.save(homeMenu);

        Menu adminMenu = new Menu();
        adminMenu.setName("后台管理");
        this.menuService.save(adminMenu);

        Menu usersAdminMenu = new Menu();
        usersAdminMenu.setName("用户管理");
        usersAdminMenu.setPath("/admin/users");
        usersAdminMenu.setParent(adminMenu);
        this.menuService.save(usersAdminMenu);

        Menu rolesAdminMenu = new Menu();
        rolesAdminMenu.setName("角色管理");
        rolesAdminMenu.setPath("/admin/roles");
        rolesAdminMenu.setParent(adminMenu);
        this.menuService.save(rolesAdminMenu);

        Menu resourcesAdminMenu = new Menu();
        resourcesAdminMenu.setName("资源管理");
        resourcesAdminMenu.setPath("/admin/resources");
        resourcesAdminMenu.setParent(adminMenu);
        this.menuService.save(resourcesAdminMenu);

        Menu menusAdminMenu = new Menu();
        menusAdminMenu.setName("菜单管理");
        menusAdminMenu.setPath("/admin/resources/menus");
        menusAdminMenu.setParent(adminMenu);
        this.menuService.save(menusAdminMenu);
    }
    //初始化菜单权限
    //admin角色对所有菜单都有权限
    private void authorizeMenu(){
        Role adminRole = this.roleService.findOne("admin");

        ResourceType menuResourceType = new ResourceType("pw.ewen.WLPT.domains.entities.resources.Menu", "menu", "系统菜单");
        ResourceRange haveAllMenuPermission = new ResourceRange("", adminRole, menuResourceType);
        haveAllMenuPermission.setMatchAll(true);
        menuResourceType.getResourceRanges().add(haveAllMenuPermission);
        this.resourceTypeService.save(menuResourceType);

        //添加ACL权限,对所有菜单有写权限（写权限包含读权限）
        permissionService.insertPermission(haveAllMenuPermission.getId(), BasePermission.WRITE);
    }

    // 初始化用户和角色，生成角色："admin"、"anonymous".生成用户："admin"
    private void initUserAndRole() {
        Role role_admin = new Role("admin", "admin");
        User user_admin = new User("admin", "admin", "admin");
        role_admin.getUsers().add(user_admin);
        this.roleService.save(role_admin);
        Role role_anonymous = new Role("anonymous", "anonymous");
        this.roleService.save(role_anonymous);
    }

    @RequestMapping(value = "/onceinit", method = RequestMethod.PUT)
    public void onceInit() {
        this.initUserAndRole();
        this.initialMenu();
        this.authorizeMenu();
    }

}
