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
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.repository.UserRepository;
import pw.ewen.WLPT.service.UserController;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

/**
 * Created by wen on 17-3-30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void init(){
        Role role1 = new Role("role1", "role1");
        roleRepository.save(role1);
        User user1 = new User("user1", "user1", "user1", role1);
        User user2 = new User("user2", "user2", "user2", role1);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void testEqual(){
        final ValidatableMockMvcResponse response = RestAssuredMockMvc.given()
                                        .standaloneSetup(new UserController(userRepository))
                                        .param("pageIndex", "0")
                                        .param("pageSize", "20")
                                        .param("filter", "name:user1")
                                    .when()
                                        .get("/users")
                                    .then()
                                        .body("content.id", hasItem("user1"));
    }
}
