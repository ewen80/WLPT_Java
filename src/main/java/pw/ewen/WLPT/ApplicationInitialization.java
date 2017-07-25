package pw.ewen.WLPT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.entities.*;
import pw.ewen.WLPT.exceptions.enviroment.PropertyNotFound;
import pw.ewen.WLPT.repositories.*;
import pw.ewen.WLPT.services.PermissionService;

import java.util.Arrays;

/**
 * Created by wen on 17-5-14.
 * 应用运行前的初始化
 */
@Component
public class ApplicationInitialization implements ApplicationRunner {

    @Autowired
    private Environment env;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResourceRangeRepository resourceRangeRepository;
    @Autowired
    private PermissionService permissionService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initialRolesAndUsers();
        this.initialMenu();
    }



    //初始化角色（默认为admin,guest）
    private void initialRoles(){
        Role adminRole = new Role("admin","admin");
        this.roleRepository.save(adminRole);

        Role guestRole = new Role("guest","guest");
        this.roleRepository.save(guestRole);
    }

    //初始化用户（默认为admin,guest）
    private void initialUsers(){
        //保存管理员用户
        Role adminRole = this.roleRepository.findByroleId("admin");
        if(adminRole != null){
            User adminUser = new User("admin","admin","admin",adminRole);
            this.userRepository.save(adminUser);
        }
        //保存来宾客户
        Role guestRole = this.roleRepository.findByroleId("guest");
        if(guestRole != null){
            User guestUser = new User("guest","guest","guest",guestRole);
            this.userRepository.save(guestUser);
        }
    }

    //初始化角色和用户
    private void initialRolesAndUsers(){
        this.initialRoles();
        this.initialUsers();
    }

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
}
