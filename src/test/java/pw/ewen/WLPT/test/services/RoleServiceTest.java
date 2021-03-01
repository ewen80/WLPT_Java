package pw.ewen.WLPT.test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.services.RoleService;

import javax.persistence.AssociationOverride;

import static org.junit.Assert.*;

/**
 * created by  wenliang on 2021-03-01
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

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

    }
}