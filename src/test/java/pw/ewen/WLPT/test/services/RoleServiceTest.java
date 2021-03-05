package pw.ewen.WLPT.test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.exceptions.domain.DeleteRoleException;
import pw.ewen.WLPT.services.ResourceRangeService;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;
import pw.ewen.WLPT.services.UserService;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @Autowired
    private ResourceRangeService resourceRangeService;
    @Autowired
    private ResourceTypeService resourceTypeService;

    @Before
    public void setUp() throws Exception {

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
        Role role1 = new Role("role1", "role1");
        User user1 = new User("user1", "user1", "");
        role1.getUsers().add(user1);
        roleService.save(role1);


        ResourceType rt = new ResourceType("ResourceTypeClass", "resourcetype");
        rt = resourceTypeService.save(rt);

        ResourceRange rr = new ResourceRange("", role1, rt);
        rr = resourceRangeService.save(rr);

        assertThatThrownBy(()-> {
            roleService.delete("role1");
        }).isInstanceOf(DeleteRoleException.class);
    }
}