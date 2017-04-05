package pw.ewen.WLPT.test.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.repository.UserRepository;
import pw.ewen.WLPT.service.UserController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by wen on 17-3-30.
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "admin")
public class TestUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void init(){
        Role role1 = new Role("role1", "role1");
        roleRepository.save(role1);
        User user1 = new User("user1", "user1", "15", role1);
        User user2 = new User("user2", "user2", "20", role1);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void testCommon() throws Exception{
        this.mvc.perform(get("/users"))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder("admin", "user1", "user2")));
    }

    /**
     * 测试用户名相等
     * @throws Exception
     */
    @Test
    public void testEqual() throws Exception{
//                given()
//                    .standaloneSetup(new UserController(userRepository))
//                    .param("pageIndex", "0")
//                    .param("pageSize", "20")
//                    .param("filter", "name:user1")
//                .when()
//                    .get("/users")
//                .then()
//                    .body("content.id", hasItem("user1"));
        this.mvc.perform(get("/users?filter={filter}", "name:user1"))
                .andExpect(jsonPath("$.content[*].id", contains("user1")));
    }

    @Test
    public void testNegation() throws Exception{
//                given()
//                    .standaloneSetup(new UserController(userRepository))
//                    .param("pageIndex", "0")
//                    .param("pageSize", "20")
//                    .param("filter", "name!:user1")
//                .when()
//                    .get("/users")
//                .then()
//                    .body("content.id", hasItem("user2"));
        this.mvc.perform(get("/users?filter={filter}", "name!user1"))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder("admin", "user2")));
    }

    @Test
    public void testGreaterThan(){
//                given()
//                    .standaloneSetup(new UserController(userRepository))
//                    .param("pageIndex", "0")
//                    .param("pageSize", "20")
//                    .param("filter", "password>15")
//                .when()
//                    .get("/users")
//                .then()
//                    .body("content.password", hasItem("20"));
    }

    @Test
    public void testGreaterThanEqual(){
//                given()
//                    .standaloneSetup(new UserController(userRepository))
//                    .param("pageIndex", "0")
//                    .param("pageSize", "20")
//                    .param("filter", "password>:20")
//                .when()
//                    .get("/users")
//                .then()
//                    .body("content.password", hasItem("20"));
    }

    @Test
    public void testLessThan(){
//                given()
//                    .standaloneSetup(new UserController(userRepository))
//                    .param("pageIndex", "0")
//                    .param("pageSize", "20")
//                    .param("filter", "password<16")
//                .when()
//                    .get("/users")
//                .then()
//                    .body("content.password", hasItem("15"));
    }

    @Test
    public void testLessThanEqual(){
//                given()
//                    .standaloneSetup(new UserController(userRepository))
//                    .param("pageIndex", "0")
//                    .param("pageSize", "20")
//                    .param("filter", "password<:15")
//                .when()
//                    .get("/users")
//                .then()
//                    .body("content.password", hasItem("15"));
    }

    @Test
    public void testStartsWith(){
//                given()
//                    .standaloneSetup(new UserController(userRepository))
//                    .param("pageIndex", "0")
//                    .param("pageSize", "20")
//                    .param("filter", "name:user*")
//                .when()
//                 .get("/users")
//                .then()
//                    .body("content.name", hasItems("user1", "user2"));
    }

    @Test
    public void testEndsWith(){
//                given()
//                    .standaloneSetup(new UserController(userRepository))
//                    .param("pageIndex", "0")
//                    .param("pageSize", "20")
//                    .param("filter", "name:*1")
//                .when()
//                 .get("/users")
//                .then()
//                  .body("content.name", hasItem("user1"));
    }

    @Test
    public void testContains(){
//                given()
//                    .standaloneSetup(new UserController(userRepository))
//                    .param("pageIndex", "0")
//                    .param("pageSize", "20")
//                    .param("filter", "name:*ser*")
//                .when()
//                 .get("/users")
//                .then()
//                    .body("content.name", hasItems("user1", "user2"));
    }
}
