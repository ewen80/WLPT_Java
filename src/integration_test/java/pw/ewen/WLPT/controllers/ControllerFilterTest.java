package pw.ewen.WLPT.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.MyResource;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.repositories.resources.MyResourceRepository;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by wen on 17-3-30.
 * 测试控制器Filter
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//如果不添加@Transactional则@Before中的语句不会每次执行方法后自动反执行,会导致insert多次数据
@Transactional
@WithMockUser(value = "admin")
public class ControllerFilterTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MyResourceRepository myResourceRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void init(){
        Role role1 = new Role("role1", "role1");
        roleRepository.save(role1);
        User user1 = new User("user1", "user1", "password", role1);
        User user2 = new User("user2", "user2", "password", role1);
        userRepository.save(user1);
        userRepository.save(user2);

        MyResource r_15 = new MyResource(15);
        MyResource r_20 = new MyResource(20);
        myResourceRepository.save(r_15);
        myResourceRepository.save(r_20);
    }

    /**
     * 测试用户名相等
     * @throws Exception
     */
    @Test
    public void testEqual() throws Exception{
        this.mvc.perform(get("/users?filter={filter}", "name:user1"))
                .andExpect(jsonPath("$.content[*].id", contains("user1")));
    }

    @Test
    public void testNegation() throws Exception{
        this.mvc.perform(get("/users?filter={filter}", "name!user1"))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder("admin", "user2")));
    }

    @Test
    public void testGreaterThan() throws Exception{
        this.mvc.perform(get("/resources/all?filter={filter}", "number>15"))
                .andExpect(jsonPath("$[*].number", containsInAnyOrder(20)));
    }

    @Test
    public void testGreaterThanEqual() throws Exception{
        this.mvc.perform(get("/resources/all?filter={filter}", "number>:20"))
                .andExpect(jsonPath("$[*].number", containsInAnyOrder(20)));
    }

    @Test
    public void testLessThan() throws Exception{
        this.mvc.perform(get("/resources/all?filter={filter}", "number<16"))
                .andExpect(jsonPath("$[*].number", containsInAnyOrder(15)));
    }

    @Test
    public void testLessThanEqual() throws Exception{
        this.mvc.perform(get("/resources/all?filter={filter}", "number<:15"))
                .andExpect(jsonPath("$[*].number", containsInAnyOrder(15)));
    }

    @Test
    public void testStartsWith() throws Exception{
        this.mvc.perform(get("/users?filter={filter}", "name:user*"))
                .andExpect(jsonPath("$.content[*].name", containsInAnyOrder("user1","user2")));
    }

    @Test
    public void testEndsWith() throws  Exception{
        this.mvc.perform(get("/users?filter={filter}", "name:*1"))
                .andExpect(jsonPath("$.content[*].name", containsInAnyOrder("user1")));
    }

    @Test
    public void testContains() throws Exception{
        this.mvc.perform(get("/users?filter={filter}", "name:*ser*"))
                .andExpect(jsonPath("$.content[*].name", containsInAnyOrder("user1","user2")));
    }

    @Test
    public void testMultiFilters() throws Exception{
        this.mvc.perform(get("/users?filter={filter}", "name:*ser*,name:user1"))
                .andExpect(jsonPath("$.content[*].name", containsInAnyOrder("user1")));
    }
}
