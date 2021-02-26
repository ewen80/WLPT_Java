package pw.ewen.WLPT.controllers;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.resources.MenuService;

/**
 * 系统首次运行初始化，一旦正式上线运行不可再调用此方法，否则菜单、账号等数据会被初始化
 */
@RestController
public class OnceInitController {

    private MenuService menuService;
    private ResourceTypeService resourceTypeService;


    //初始化菜单数据
    private void initialMenu() {
        Menu homeMenu = new Menu();
        homeMenu.setName("Home");
        homeMenu.setPath("/");
        menuRepository.save(homeMenu);

        Menu adminMenu = new Menu();
        adminMenu.setName("后台管理");
        menuRepository.save(adminMenu);

        Menu usersAdminMenu = new Menu();
        usersAdminMenu.setName("用户管理");
        usersAdminMenu.setPath("/admin/users");
        usersAdminMenu.setParent(adminMenu);
        menuRepository.save(usersAdminMenu);

        Menu rolesAdminMenu = new Menu();
        rolesAdminMenu.setName("角色管理");
        rolesAdminMenu.setPath("/admin/roles");
        rolesAdminMenu.setParent(adminMenu);
        menuRepository.save(rolesAdminMenu);

        Menu resourcesAdminMenu = new Menu();
        resourcesAdminMenu.setName("资源管理");
        resourcesAdminMenu.setPath("/admin/resources");
        resourcesAdminMenu.setParent(adminMenu);
        menuRepository.save(resourcesAdminMenu);

        Menu menusAdminMenu = new Menu();
        menusAdminMenu.setName("菜单管理");
        menusAdminMenu.setPath("/admin/resources/menus");
        menusAdminMenu.setParent(adminMenu);
        menuRepository.save(menusAdminMenu);
    }
    //初始化菜单权限
    //admin角色对所有菜单都有权限
    private void authorizeMenu(){
        ResourceType menuResourceType = new ResourceType("pw.ewen.WLPT.domains.entities.resources.Menu", "menu", "系统菜单");
        this.resourceTypeRepository.save(menuResourceType);

        Role adminRole = this.roleRepository.findOne("admin");

        ResourceRange haveAllMenuPermission = new ResourceRange("", adminRole, menuResourceType);
        haveAllMenuPermission.setMatchAll(true);
        this.resourceRangeRepository.save(haveAllMenuPermission);

        //添加ACL权限
        permissionService.insertPermission(haveAllMenuPermission.getId(), BasePermission.ADMINISTRATION);
    }

}
