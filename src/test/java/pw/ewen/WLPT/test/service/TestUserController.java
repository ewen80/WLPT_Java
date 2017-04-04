package pw.ewen.WLPT.test.service;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.repository.UserRepository;
import pw.ewen.WLPT.service.UserController;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

/**
 * Created by wen on 17-3-30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

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
    public void testEqual(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new UserController(userRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "name:user1")
                .when()
                    .get("/users")
                .then()
                    .body("content.id", hasItem("user1"));
    }

    @Test
    public void testNegation(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new UserController(userRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "name!:user1")
                .when()
                    .get("/users")
                .then()
                    .body("content.id", hasItem("user2"));
    }

    @Test
    public void testGreaterThan(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new UserController(userRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "password>15")
                .when()
                    .get("/users")
                .then()
                    .body("content.password", hasItem("20"));
    }

    @Test
    public void testGreaterThanEqual(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new UserController(userRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "password>:20")
                .when()
                    .get("/users")
                .then()
                    .body("content.password", hasItem("20"));
    }

    @Test
    public void testLessThan(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new UserController(userRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "password<16")
                .when()
                    .get("/users")
                .then()
                    .body("content.password", hasItem("15"));
    }

    @Test
    public void testLessThanEqual(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new UserController(userRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "password<:15")
                .when()
                    .get("/users")
                .then()
                    .body("content.password", hasItem("15"));
    }

    @Test
    public void testStartsWith(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new UserController(userRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "name:user*")
                .when()
                 .get("/users")
                .then()
                    .body("content.name", hasItems("user1", "user2"));
    }

    @Test
    public void testEndsWith(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new UserController(userRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "name:*1")
                .when()
                 .get("/users")
                .then()
                  .body("content.name", hasItem("user1"));
    }

    @Test
    public void testContains(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new UserController(userRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "name:*ser*")
                .when()
                 .get("/users")
                .then()
                    .body("content.name", hasItems("user1", "user2"));
    }
}
