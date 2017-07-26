package pw.ewen.WLPT.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import pw.ewen.WLPT.annotations.WithAdminUser;
import pw.ewen.WLPT.domains.entities.MyResource;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.MyResourceRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
//@WithAdminUser(authorities = "admin")
@WithAdminUser
public class ControllerFilterTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MyResourceRepository myResourceRepository;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @Before
    public void init(){
        //将spring security注入spring mvc test
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Role role1 = new Role("role1", "role1");
        roleRepository.save(role1);
        User user1 = new User("user1", "user1", "password", role1);
        User user2 = new User("user2", "user2", "password", role1);
        userRepository.save(user1);
        userRepository.save(user2);

    }

    /**
     * 测试用户名相等
     * @throws Exception
     */
    @Test
    public void testEqual() throws Exception{
         this.mvc.perform(get("/users?filter={filter}", "name:user1"))
                 .andDo(print())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].userId", is("user1")));
    }

    @Test
    public void testNegation() throws Exception{
        this.mvc.perform(get("/users?filter={filter}", "name!user1"))
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[*].userId", containsInAnyOrder("admin", "guest", "user2")));
    }

    @Test
    public void testStartsWith() throws Exception{
        this.mvc.perform(get("/users?filter={filter}", "name:user*"))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("user1","user2")));
    }

    @Test
    public void testEndsWith() throws  Exception{
        this.mvc.perform(get("/users?filter={filter}", "name:*1"))
                .andDo(print())
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("user1")));
    }

    @Test
    public void testContains() throws Exception{
        this.mvc.perform(get("/users?filter={filter}", "name:*ser*"))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("user1","user2")));
    }

    @Test
    public void testMultiFilters() throws Exception{
        this.mvc.perform(get("/users?filter={filter}", "name:*ser*,name:user1"))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("user1")));
    }
}
