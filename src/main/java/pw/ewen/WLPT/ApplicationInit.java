package pw.ewen.WLPT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.RoleService;
import pw.ewen.WLPT.services.UserService;

import java.util.Set;

/**
 * created by wenliang on 2021/3/6
 */
@Component
@Transactional
public class ApplicationInit implements ApplicationRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Role r1 = new Role("r1", "r1name");
//        roleService.save(r1);
//
//        User u1  = new User("u1", "u1name", "", r1);
//        userService.save(u1);
//
//        Set<User> users = roleService.findOne("r1").getUsers();
//        users.size();
    }
}
