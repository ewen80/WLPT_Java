package pw.ewen.WLPT.test.repository.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by wenliang on 2021-03-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoleTest {

    @Autowired
    private RoleService roleService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void setUsers() {
        Role r1 = new Role("r1", "r1name");
        User u1 = new User("u1", "u1name", "u1p", r1);
        r1.getUsers().add(u1);
        roleService.save(r1);

//        // 将hibernate session内的内存改动进行提交，否则事务会将修改数据库的动作回滚，造成无法执行update,insert,delete动作
//        entityManager.flush();
//        // 将hibernate的缓存清空，否则findOne不从数据库中读取
//        entityManager.clear();

        Set<User> empty = new HashSet<>();
        r1.setUsers(empty);
        roleService.save(r1);

        assertThat(r1.getUsers()).isEmpty();
    }
}