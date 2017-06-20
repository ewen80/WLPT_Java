package pw.ewen.WLPT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.Resource;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.resources.MenuRepository;
import pw.ewen.WLPT.services.PermissionService;

import java.util.Arrays;

/**
 * Created by wen on 17-5-14.
 * 应用运行前的初始化
 */
@Component
public class ApplicationInitialization implements ApplicationRunner {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourceRangeRepository resourceRangeRepository;
    @Autowired
    private PermissionService permissionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initialMenu();
        this.authorizeMenu();
    }

    //初始化菜单数据
    private void initialMenu() {
        Menu menu1 = new Menu();
        menu1.setName("menu1");
        menu1.setPath("path1");
        menuRepository.save(menu1);

        Menu menu11 = new Menu();
        menu11.setName("menu11");
        menu11.setPath("path11");
        menu11.setOrderId(2);
        menu11.setParent(menu1);
        menuRepository.save(menu11);

        Menu menu12 = new Menu();
        menu12.setName("menu12");
        menu12.setPath("path12");
        menu12.setOrderId(1);
        menu12.setParent(menu1);
        menuRepository.save(menu12);
    }
    //初始化菜单权限
    private void authorizeMenu(){
        ResourceType menuResourceType = new ResourceType("pw.ewen.WLPT.domains.entities.resources.Menu", "menu");
        this.resourceTypeRepository.save(menuResourceType);

        Role adminRole = this.roleRepository.findOne("admin");

        ResourceRange haveAllMenuPermission = new ResourceRange("", adminRole, menuResourceType);
        haveAllMenuPermission.setMatchAll(true);
        this.resourceRangeRepository.save(haveAllMenuPermission);
    }
}
