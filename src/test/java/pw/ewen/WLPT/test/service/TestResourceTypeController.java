package pw.ewen.WLPT.test.service;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pw.ewen.WLPT.domain.entity.ResourceType;
import pw.ewen.WLPT.repository.ResourceTypeRepository;
import pw.ewen.WLPT.service.ResourceTypeController;
import pw.ewen.WLPT.service.UserController;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;

/**
 * Created by wen on 17-4-2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResourceTypeController {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Before
    public void init(){
        ResourceType rt1 = new ResourceType("a","a","a",true);
        ResourceType rt2 = new ResourceType("b","b","b",false);
        resourceTypeRepository.save(rt1);
        resourceTypeRepository.save(rt2);
    }

    @Test
    public void testBooleanEqual_CanConvertToBoolean(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new ResourceTypeController(resourceTypeRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "deleted:true")
                .when()
                    .get("/resourcetypes")
                .then()
                .body("content.className", hasItem("a"));
    }

    @Test(expected = Exception.class)
    public void testBooleanEqual_CanNotConvertToBoolean(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new ResourceTypeController(resourceTypeRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "deleted:True")
                .when()
                  .get("/resourcetypes");
    }

    @Test
    public void testBooleanEqual_CancelFilter(){
        RestAssuredMockMvc
                .given()
                    .standaloneSetup(new ResourceTypeController(resourceTypeRepository))
                    .param("pageIndex", "0")
                    .param("pageSize", "20")
                    .param("filter", "deleted:true")
                .when()
                    .get("/resourcetypes")
                .then()
                    .body("content.className", hasItems("a", "b"));
    }
}
