package pw.ewen.WLPT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.*;
import pw.ewen.WLPT.services.resources.MenuService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * created by wenliang on 2021/3/6
 * 系统初始化工作。对用户、角色和权限配置进行初始化。
 */
@Component
public class ApplicationInit implements ApplicationRunner {
    private RoleService roleService;
    private UserService userService;
    private MenuService menuService;
    private ResourceTypeService resourceTypeService;
    private ResourceRangeService resourceRangeService;
    private PermissionService permissionService;


    @Autowired
    public ApplicationInit(RoleService roleService,
                           UserService userService,
                           MenuService menuService,
                           ResourceTypeService resourceTypeService,
                           ResourceRangeService resourceRangeService,
                           PermissionService permissionService) {
        this.roleService = roleService;
        this.userService = userService;
        this.menuService = menuService;
        this.resourceTypeService = resourceTypeService;
        this.resourceRangeService = resourceRangeService;
        this.permissionService = permissionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initUserAndRole();
        initialMenu();
    }

    // 初始化用户和角色，生成角色："admin"、"anonymous".生成用户："admin"
    private void initUserAndRole() {
        // 检查角色表中是否有anonymous角色,没有就新建
        Role anonymousRole = roleService.findOne("anonymous");
        if(anonymousRole == null) {
            roleService.save(new Role("anonymous", "anonymous"));
        }

        Role adminRole = roleService.findOne("admin");
        if(adminRole == null) {
            // 新建admin角色
            adminRole = roleService.save(new Role("admin", "admin"));
        }

        // 检查是否存在admin用户，没有就新建，默认加入admin角色
        User adminUser = userService.findOne("admin");
        if(adminUser == null) {
            //新建用户admin
            User user = new User("admin", "管理员", "admin", adminRole);
            adminRole.getUsers().add(user);
            userService.save(user);
        }
    }

    //初始化菜单数据
    private void initialMenu() {

        SearchSpecificationsBuilder<Menu> builder = new SearchSpecificationsBuilder<>();
        Menu    homeMenu = null,
                adminMenu = null,
                usersAdminMenu = null,
                rolesAdminMenu = null,
                resourcesAdminMenu = null,
                menusAdminMenu = null;

        List<Menu> homeMenus = menuService.findAll(builder.build("name:Home"));
        if(homeMenus.size() == 0) {
            homeMenu = new Menu();
            homeMenu.setName("Home");
            homeMenu.setPath("/");
            this.menuService.save(homeMenu);
        }

        List<Menu> adminMenus = menuService.findAll(builder.build("name:后台管理"));
        if(adminMenus.size() == 0) {
            adminMenu = new Menu();
            adminMenu.setName("后台管理");
            this.menuService.save(adminMenu);
        }

        List<Menu> usersAdminMenus = menuService.findAll(builder.build("name:用户管理"));
        if(usersAdminMenus.size() == 0) {
            usersAdminMenu = new Menu();
            usersAdminMenu.setName("用户管理");
            usersAdminMenu.setPath("/admin/users");
            usersAdminMenu.setParent(adminMenu);
            this.menuService.save(usersAdminMenu);
        }

        List<Menu> rolesAdminMenus = menuService.findAll(builder.build("name:角色管理"));
        if(rolesAdminMenus.size() == 0) {
            rolesAdminMenu = new Menu();
            rolesAdminMenu.setName("角色管理");
            rolesAdminMenu.setPath("/admin/roles");
            rolesAdminMenu.setParent(adminMenu);
            this.menuService.save(rolesAdminMenu);
        }

        List<Menu> resourcesAdminMenus = menuService.findAll(builder.build("name:资源管理"));
        if(resourcesAdminMenus.size() == 0) {
            resourcesAdminMenu = new Menu();
            resourcesAdminMenu.setName("资源管理");
            resourcesAdminMenu.setPath("/admin/resources");
            resourcesAdminMenu.setParent(adminMenu);
            this.menuService.save(resourcesAdminMenu);
        }

        List<Menu> menusAdminMenus = menuService.findAll(builder.build("name:菜单管理"));
        if(menusAdminMenus.size() == 0) {
            menusAdminMenu = new Menu();
            menusAdminMenu.setName("菜单管理");
            menusAdminMenu.setPath("/admin/resources/menus");
            menusAdminMenu.setParent(adminMenu);
            this.menuService.save(menusAdminMenu);
        }

    }

}
