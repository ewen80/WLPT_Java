package pw.ewen.WLPT.test.service;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pw.ewen.WLPT.domain.entity.ResourceRange;
import pw.ewen.WLPT.domain.entity.ResourceType;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.repository.ResourceRangeRepository;
import pw.ewen.WLPT.repository.ResourceTypeRepository;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.service.ResourceRangeController;
import pw.ewen.WLPT.service.ResourceTypeController;

import static org.hamcrest.Matchers.hasItem;

/**
 * Created by wen on 17-4-3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResouceRangeController {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private ResourceRangeRepository resourceRangeRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void init(){
        ResourceType rt1 = new ResourceType("className1","name1","",false);
        resourceTypeRepository.save(rt1);
        ResourceType rt2 = new ResourceType("className2","name2","",false);
        resourceTypeRepository.save(rt2);

        Role role1 = new Role("role1", "role1");
        roleRepository.save(role1);
        Role role2 = new Role("role2", "role2");
        roleRepository.save(role2);

        ResourceRange rr1 = new ResourceRange("filter1", role1, rt1);
        rr1 = resourceRangeRepository.save(rr1);
        ResourceRange rr2 = new ResourceRange("filter2", role2, rt2);
        rr2 = resourceRangeRepository.save(rr2);
    }

    @Test
    public void testGetByResourceTypeName_MatchOne(){
        RestAssuredMockMvc
            .given()
                .standaloneSetup(new ResourceRangeController(resourceRangeRepository))
                .param("resourcetype", "className1")
            .when()
                .get("/resourceranges")
            .then()
                .body("content.filter", hasItem("filter1"));
    }

    @Test
    public void testGetByResourceTypeName_NoMatch(){

    }
}
