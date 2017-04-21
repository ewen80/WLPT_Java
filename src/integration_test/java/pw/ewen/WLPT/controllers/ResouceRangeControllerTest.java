package pw.ewen.WLPT.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * Created by wen on 17-4-3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(value = "admin")
public class ResouceRangeControllerTest {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private ResourceRangeRepository resourceRangeRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mvc;

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
        ResourceRange rr3 = new ResourceRange("filter3", role2, rt1);
        rr3 = resourceRangeRepository.save(rr3);
    }

    /**
     * 根据ResouceType得到ResourceRange
     * @throws Exception
     */
    @Test
    public void testGetByResourceTypeName_Match() throws Exception{
        this.mvc.perform(get("/resourceranges?resourceclassname={resourceType}", "className1"))
                .andExpect(jsonPath("$[*].filter",containsInAnyOrder("filter1", "filter3")));
    }

    @Test
    public void testGetByResourceTypeName_NoMatch() throws Exception{
        this.mvc.perform(get("/resourceranges?resourceclassname={resourceType}", "className3"))
                .andExpect(jsonPath("$[*].filter", hasSize(0)));
    }

    //测试正常的保存功能
    @Test
    public void testSave_common() throws Exception{

        this.mvc.perform(post("/resourceranges")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"resourceTypeClassName\":\"className1\",\"filter\":\"a=1\",\"roleId\":\"role1\",\"matchAll\":\"false\"}"))
//                            .param("filter","filter")
//                            .param("roleId","role1")
//                            .param("resourceTypeClassName","className1"))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect((jsonPath("$.filter", is("a=1"))));
    }
}
