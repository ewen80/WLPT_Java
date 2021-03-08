package pw.ewen.WLPT.test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.exceptions.domain.DeleteRoleException;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.services.ResourceRangeService;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;
import pw.ewen.WLPT.services.UserService;

import javax.persistence.AssociationOverride;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Set;

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
//    @Autowired
//    private ResourceRangeService resourceRangeService;
    @Autowired
    private ResourceTypeService resourceTypeService;

    private Role r1;
    private User u1;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        r1 = new Role("r1", "r1name");
        roleService.save(r1);

        u1  = new User("u1", "u1name", "", r1);
        userService.save(u1);

        // 将hibernate session内的内存改动进行提交，否则事务会将修改数据库的动作回滚，造成无法执行update,insert,delete动作
        entityManager.flush();
        // 将hibernate的缓存清空，否则findOne不从数据库中读取
        entityManager.clear();
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
        // 角色r1下面有用户u1,删除失败
        assertThatThrownBy( () -> {
            roleService.delete("r1");
        }).isInstanceOf(DeleteRoleException.class);
        // 角色r1下面有权限配置，删除失败
        ResourceType rt1  = new ResourceType("class1", "rt1");
        ResourceRange rr1 = new ResourceRange("", r1, rt1);
        rt1.getResourceRanges().add(rr1);
        resourceTypeService.save(rt1);

//        entityManager.flush();
        assertThatThrownBy( () -> {
            roleService.delete("r1");
        }).isInstanceOf(DeleteRoleException.class);

        // 多个角色的情况
        Role r2 = new Role("r2", "r2name");
        User u2 = new User("u2", "u2name", "", r2);
        r2.getUsers().add(u2);
        roleService.save(r2);
         assertThatThrownBy(() -> {
             roleService.delete(new String[]{"r1", "r2"});
         }).isInstanceOf(DeleteRoleException.class);

         // 可以删除
        Role r3 = new Role("r3", "r3name");
        roleService.save(r3);
        roleService.delete("r3");

    }

    @Test
    public void getUsers() {
        // 一对多关系
        assertEquals(1, roleService.findOne("r1").getUsers().size());
    }

    @Test
    public  void cascade() {
        r1 = roleService.findOne("r1");

        // 级联保存
        User u2 = new User("u2", "u2name", "", r1);
        r1.getUsers().add(u2);
        roleService.save(r1);

        entityManager.flush();

        assertEquals(2, userService.findAll().size());
    }
}