package pw.ewen.WLPT.test.repository.specifications;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domain.entity.MyResource;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.repository.MyResourceRepository;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.repository.UserRepository;
import pw.ewen.WLPT.repository.specifications.MyResourceSpecification;
import pw.ewen.WLPT.repository.specifications.SearchCriteria;
import pw.ewen.WLPT.repository.specifications.SearchOperation;
import pw.ewen.WLPT.repository.specifications.SearchSpecification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wen on 17-3-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
public class TestResourceTypeSpecification {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MyResourceRepository myResourceRepository;

    private User user1;
    private User user2;
    private Role role1;
    private MyResource resource1;
    private MyResource resource2;

    @Before
    public void init(){
        Role role1 = new Role("role1", "role1");
        roleRepository.save(role1);

        this.user1 = new User("user1", "user1", "15", role1);
        this.user2 = new User("user2", "user2", "20", role1);
        userRepository.save(user1);
        userRepository.save(user2);

        this.resource1 = new MyResource(15);
        this.resource2 = new MyResource(20);
        myResourceRepository.save(resource1);
        myResourceRepository.save(resource2);

    }
    @Test
    public void testEqual() {
        SearchSpecification spec1 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.EQUALITY, "user1"));
        SearchSpecification spec2 = new SearchSpecification(
                new SearchCriteria("id", SearchOperation.EQUALITY, "user1"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec1).and(spec2));

        assertThat(user1).isIn(results);
        assertThat(user2).isNotIn(results);
    }

    @Test
    public void testNegation() {
        SearchSpecification spec1 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.NEGATION, "user1"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec1));

        assertThat(user2).isIn(results);
        assertThat(user1).isNotIn(results);
    }

    @Test
    public void testGreaterThan(){
        SearchSpecification spec = new SearchSpecification(
                new SearchCriteria("password", SearchOperation.GREATER_THAN, "15"));
        List<MyResource> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(user1).isNotIn(results);
        assertThat(user2).isIn(results);
    }
}
