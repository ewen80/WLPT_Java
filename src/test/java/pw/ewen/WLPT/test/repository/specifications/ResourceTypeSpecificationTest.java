package pw.ewen.WLPT.test.repository.specifications;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.MyResource;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.MyResourceRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchCriteria;
import pw.ewen.WLPT.repositories.specifications.core.SearchOperation;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wen on 17-3-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
public class ResourceTypeSpecificationTest {

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
                new SearchCriteria("userId", SearchOperation.EQUALITY, "user1"));
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
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(user1).isNotIn(results);
        assertThat(user2).isIn(results);
    }

    @Test
    public void testGreaterThanEqual(){
        SearchSpecification spec = new SearchSpecification(
                new SearchCriteria("password", SearchOperation.GREATER_THAN_EQUALITY, "20"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(user1).isNotIn(results);
        assertThat(user2).isIn(results);
    }

    @Test
    public void testLessThan(){
        SearchSpecification spec = new SearchSpecification(
                new SearchCriteria("password", SearchOperation.LESS_THAN, "16"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(user2).isNotIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testLessThanEqual(){
        SearchSpecification spec = new SearchSpecification(
                new SearchCriteria("password", SearchOperation.LESS_THAN_EQUALITY, "15"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(user2).isNotIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testLike1(){
        SearchSpecification spec = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.LIKE, "user"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(results).isEmpty();
//        assertThat(user2).isNotIn(results);
//        assertThat(user1).isNotIn(results);
    }

    @Test
    public void testLike2(){
        SearchSpecification spec = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.LIKE, "user%"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(user2).isIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testStartsWith(){
        SearchSpecification spec = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.STARTS_WITH, "user"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(user2).isIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testEndsWith(){
        SearchSpecification spec = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.ENDS_WITH, "er1"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(user2).isNotIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testContains(){
        SearchSpecification spec = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.CONTAINS, "ser"));
        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec));
        assertThat(user2).isIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testMultiFilters(){
        SearchSpecification spec1 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.EQUALITY, "user1"));
        SearchSpecification spec2 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.CONTAINS, "ser"));

        List<User> results = userRepository.findAll(org.springframework.data.jpa.domain.Specifications.where(spec1).and(spec2));
        assertThat(user1).isIn(results);
        assertThat(user2).isNotIn(results);
    }
}
