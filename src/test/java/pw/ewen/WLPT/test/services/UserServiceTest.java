package pw.ewen.WLPT.test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.services.RoleService;

/**
 * created by wenliang on 20210228
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    private Role role1;
    private User  user1, user2, user3;

    @Before
    public void setUp() throws Exception {
        role1 = new Role("role1", "role1");
        user1 = new User("user1", "user1", "", role1);
        roleService.save(role1);
    }

    // 能够找到所有users
    @Test
    public void findAll() {
    }

    @Test
    public void testFindAll() {
    }

    @Test
    public void findOne() {
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }
}