package pw.ewen.WLPT.test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.RoleService;
import pw.ewen.WLPT.services.UserService;

import javax.persistence.AssociationOverride;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * created by  wenliang on 2021-03-01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    private Role r1;
    private User u1;

    @Before
    public void setUp() throws Exception {
        r1 = new Role("r1", "r1name");
        roleService.save(r1);

        u1  = new User("u1", "u1name", "", r1);
        userService.save(u1);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void testFindAll() {
    }

    @Test
    public void save() {
        Role role1 = new Role("role1", "role1");
        this.roleService.save(role1);
        assertEquals(this.roleService.findOne("role1"), role1);
    }

    @Test
    public void delete() {

    }

    @Test
//    @Transactional
    public void getUsers() {


        Set<User> users = r1.getUsers();

//        assertNotNull(userService.findOne("u1"));
        assertEquals(1, users.size());
    }
}