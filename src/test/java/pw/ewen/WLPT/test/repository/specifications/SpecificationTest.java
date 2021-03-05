package pw.ewen.WLPT.test.repository.specifications;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.MyResource;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.resources.MyResourceRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wen on 17-3-29.
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class SpecificationTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MyResourceRepository myResourceRepository;

    private User user1;
    private User user2;
    private User user3;
    private Role role1;
    private MyResource resource1;
    private MyResource resource2;

    @Before
    public void init(){
        Role role1 = new Role("role1", "role1");
        roleRepository.save(role1);

        this.user1 = new User("user1", "user1", "15");
        this.user2 = new User("user2", "user2", "20");
        this.user3 = new User("user3", "user3", "25");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        this.resource1 = new MyResource(15);
        this.resource2 = new MyResource(20);
        myResourceRepository.save(resource1);
        myResourceRepository.save(resource2);

    }
    @Test
    public void testEqual() {
        String filter = "name:user1";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user1).isIn(results);

        filter = "id:user1";
        results = userRepository.findAll(builder.build(filter));
        assertThat(user2).isNotIn(results);
    }

    @Test
    public void testNegation() {
        String filter = "name!user1";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));

        assertThat(user2).isIn(results);
        assertThat(user1).isNotIn(results);
    }

    @Test
    public void testGreaterThan(){
        String filter = "password>15";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user1).isNotIn(results);
        assertThat(user2).isIn(results);
    }

    @Test
    public void testGreaterThanEqual(){
        String filter = "password>:20";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user1).isNotIn(results);
        assertThat(user2).isIn(results);
    }

    @Test
    public void testLessThan(){
        String filter = "password<16";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user2).isNotIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testLessThanEqual(){
        String filter = "password<:15";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user2).isNotIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testLike1(){
        String filter = "name~user";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(results).isEmpty();
//        assertThat(user2).isNotIn(results);
//        assertThat(user1).isNotIn(results);
    }

    @Test
    public void testLike2(){
        String filter = "name~user%";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user2).isIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testStartsWith(){
        String filter = "name:user*";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user2).isIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testEndsWith(){
        String filter = "name:*er1";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user2).isNotIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testContains(){
        String filter = "name:*ser*";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user2).isIn(results);
        assertThat(user1).isIn(results);
    }

    @Test
    public void testIn() {
        String filter = "name()user1_user2";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(user1).isIn(results);
        assertThat(user2).isIn(results);
        assertThat(user3).isNotIn(results);
    }

    @Test
    public void testMultiFilters(){
        String filter = "name:user1,name:*ser*";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));

        assertThat(user1).isIn(results);
        assertThat(user2).isNotIn(results);
    }
}
