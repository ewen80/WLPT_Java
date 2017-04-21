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
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by wen on 17-4-2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@WithMockUser(username = "admin")
public class ResourceTypeControllerTest {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void init(){
        ResourceType rt1 = new ResourceType("a","a","a",true);
        ResourceType rt2 = new ResourceType("b","b","b",false);
        resourceTypeRepository.save(rt1);
        resourceTypeRepository.save(rt2);
    }

    /**
     * 测试filter表达式中包含true,false是否能转为boolean型进行过滤
     */
    @Test
    public void testBooleanEqual_CanConvertToBoolean() throws Exception{

        this.mvc.perform(get("/resourcetypes?filter={filter}", "deleted:true"))
                .andExpect(jsonPath("$.content[*].className", containsInAnyOrder("a")));
    }

    /**
     * 测试不能转为boolean的表达式(只有true和false能转为boolean表达式，必须为小写)
     * 此处应该出现异常，因为deleted字段为boolean，True无法转为boolean型，所以当将字符串和boolean进行对比，系统报错。
     */
    @Test(expected = Exception.class)
    public void testBooleanEqual_CanNotConvertToBoolean() throws Exception{
        this.mvc.perform(get("/resourcetypes?filter={filter}", "deleted:True"));

    }
}
