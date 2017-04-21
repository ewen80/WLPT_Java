package pw.ewen.WLPT.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.BasePermission;
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
import pw.ewen.WLPT.services.PermissionService;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by wenliang on 17-4-17.
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class PermissionControllerTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourceRangeRepository resourceRangeRepository;
    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MockMvc mvc;

    private Role role1;
    private ResourceRange rr1;
    private ResourceType rt1;

    @Before
    public void init(){
        this.role1 = new Role("role1", "role1");
        roleRepository.save(this.role1);

        this.rt1 = new ResourceType("className1","name1","",false);
        resourceTypeRepository.save(rt1);

        this.rr1 = new ResourceRange("filter1", this.role1, this.rt1);
        this.rr1 = resourceRangeRepository.save(this.rr1);
    }


    /**
     * ResourceRange和Role都存在
     * @throws Exception
     */
    @Test
    @WithMockUser(value = "admin", authorities = "admin")
    public void HaveResourceRangeAndRole() throws Exception{
        this.permissionService.insertPermission(this.rr1, this.role1, BasePermission.READ);
        this.mvc.perform(get("/permissions?resourceRangeId={resourceRangeId}&roleId={roleId}", rr1.getId(), role1.getId()))
                .andExpect(jsonPath("$[*].resourceRangeId",containsInAnyOrder(Math.toIntExact(rr1.getId()))));
    }

    /**
     * ResourceRange或者Role不存在的情况，应该返回空数组
     * @throws Exception
     */
    @Test
    @WithMockUser(value = "admin", authorities = "admin")
    public void NoRole() throws Exception {

        Role noRole = new Role("noRole", "noRoleName");
        this.permissionService.insertPermission(rr1, role1, BasePermission.READ);

        this.mvc.perform(get("/permissions?resourceRangeId={resourceRangeId}&roleId={roleId}", this.rr1.getId(), noRole.getId()))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    @WithMockUser(value = "admin", authorities = "admin")
    public void NoResourceRange() throws  Exception {

        this.permissionService.insertPermission(rr1, role1, BasePermission.READ);

        this.mvc.perform(get("/permissions?resourceRangeId={resourceRangeId}&roleId={roleId}", 9999, this.role1.getId()))
                .andExpect(jsonPath("$", hasSize(0)));

    }
}
