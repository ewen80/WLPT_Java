package pw.ewen.WLPT.test.repository.specifications;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.Resource;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.MyResource;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.resources.MyResourceRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.ResourceRangeService;
import pw.ewen.WLPT.services.ResourceTypeService;

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
    @Autowired
    ResourceTypeService resourceTypeService;
    @Autowired
    ResourceRangeService resourceRangeService;

    private User user1;
    private User user2;
    private User user3;
    private Role role1;
    private MyResource resource1;
    private MyResource resource2;

    @Before
    public void init(){
        role1 = new Role("role1", "role1");
        roleRepository.save(role1);

        this.user1 = new User("user1", "user1", "15", role1);
        this.user2 = new User("user2", "user2", "20", role1);
        this.user3 = new User("user3", "user3", "25", role1);
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
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();

        String filter = "name:用户";
        List<User> results = userRepository.findAll(builder.build(filter));
        assertThat(results).isEmpty();

        builder.reset();

        filter = "id:user1";
        results = userRepository.findAll(builder.build(filter));
        assertThat(user1).isIn(results);
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
    public void testNestedProperty() {
        ResourceType type1 = new ResourceType("p1.p2.p3.c1", "p1.p2.p3.c1");
        ResourceRange range1 = new ResourceRange("", role1, type1);
        type1.getResourceRanges().add(range1);
        resourceTypeService.save(type1);

        ResourceType type2 = new ResourceType("p1.p2.p3.c2", "p1.p2.p3.c2");
        ResourceRange range2 = new ResourceRange("", role1, type2);
        type2.getResourceRanges().add(range2);
        resourceTypeService.save(type2);

        // 测试单条件
        String filter = "resourceType.className:p1.p2.p3.c1";
        SearchSpecificationsBuilder<ResourceRange> builder = new SearchSpecificationsBuilder<>();
        List<ResourceRange> results1 = resourceRangeService.findAll(builder.build(filter));
        assertThat(results1).hasSize(1);
        builder.reset();

        filter = "resourceType.className:p1.p2.p3.c0";
        List<ResourceRange> results2 = resourceRangeService.findAll(builder.build(filter));
        assertThat(results2).isEmpty();
        builder.reset();

        // 测试多条件
        filter = "resourceType.className:p1.p2.p3.c1,role.id:role1";
        List<ResourceRange> results3 = resourceRangeService.findAll(builder.build(filter));
        assertThat(results3).hasSize(1);
        builder.reset();

        filter = "resourceType.className:p1.p2.p3.c0,role.id:role1";
        List<ResourceRange> results4 = resourceRangeService.findAll(builder.build(filter));
        assertThat(results4).isEmpty();
        builder.reset();
    }

    @Test
    public void testUserRoleId() {
        Role r1 = new Role("r1", "r1");
        User u1 = new User("u1", "u1", "", r1);
        r1.getUsers().add(u1);
        this.roleRepository.save(r1);

        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();

        String filter = "role.id:*r1*";
        List<User> users = this.userRepository.findAll(builder.build(filter));

        assertThat(users).hasSize(1);
    }

    @Test
    public void testMultiFilters(){
        String filter = "name:user1,name:user2";
        SearchSpecificationsBuilder<User> builder = new SearchSpecificationsBuilder<>();
        List<User> results = userRepository.findAll(builder.build(filter));

        assertThat(results).isEmpty();
    }
}
