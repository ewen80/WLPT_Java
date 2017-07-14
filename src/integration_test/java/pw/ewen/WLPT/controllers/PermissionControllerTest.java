package pw.ewen.WLPT.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.ResourceRangePermissionWrapperDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.services.PermissionService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by wenliang on 17-4-17.
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
@WithMockUser(value = "admin", authorities = "admin")
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

    private ResourceRange rr1, rr2;
    private ResourceType rt1;

    @Before
    public void init(){
        Role role1 = new Role("role1", "role1");
        roleRepository.save(role1);

        this.rt1 = new ResourceType("className1","name1","",false);
        resourceTypeRepository.save(rt1);

        this.rr1 = new ResourceRange("filter1", role1, this.rt1);
        this.rr1 = resourceRangeRepository.save(this.rr1);

        this.rr2 = new ResourceRange("filter2", role1, this.rt1);
        this.rr2 = resourceRangeRepository.save(this.rr2);
    }


    /**
     * ResourceRange和Role都存在
     * @throws Exception
     */
    @Test
    public void HaveResourceRangeAndRole() throws Exception{
        this.permissionService.insertPermission(this.rr1.getId(), BasePermission.READ);
        this.mvc.perform(get("/permissions/{resourceRangeId}", rr1.getId()))
                .andExpect(jsonPath("$[*].resourceRangeId", containsInAnyOrder(Math.toIntExact(rr1.getId()))))
                .andExpect(jsonPath("$[*].permissions[*].mask", containsInAnyOrder(BasePermission.READ.getMask())));
    }

    /**
     * 获取多个ResourceRanges的权限
     * @throws Exception
     */
    @Test
    public void MultiResouceRanges() throws  Exception {
        this.permissionService.insertPermission(this.rr1.getId(), BasePermission.READ);
        this.permissionService.insertPermission(this.rr2.getId(), BasePermission.WRITE);

        String ids = new StringBuilder().append(this.rr1.getId()).append(',').append(this.rr2.getId()).toString();
        this.mvc.perform(get("/permissions/{resourceRangeIds}", ids))
                .andExpect(jsonPath("$[*].resourceRangeId", containsInAnyOrder(Math.toIntExact(rr1.getId()),Math.toIntExact(rr2.getId()))))
                .andExpect(jsonPath("$[*].permissions[*].mask", containsInAnyOrder(BasePermission.READ.getMask(),BasePermission.WRITE.getMask())));
    }

    /**
     * ResourceRange不存在的情况，应该返回空数组
     * @throws Exception
     */
    @Test
    public void NoResourceRange() throws  Exception {

        this.permissionService.insertPermission(rr1.getId(), BasePermission.READ);

        this.mvc.perform(get("/permissions/0"))
                .andExpect(status().isNotFound());

    }

    /**
     * 测试插入一条permission
     * @throws Exception
     */
    @Test
    public void save_insertPermission() throws Exception {
        ResourceRangePermissionWrapperDTO dto = new ResourceRangePermissionWrapperDTO();
        dto.setResourceRangeId(rr1.getId());

        Set<PermissionDTO> permissions = new HashSet<>();
        permissions.add(new PermissionDTO(BasePermission.READ.getMask()));
        permissions.add(new PermissionDTO(BasePermission.WRITE.getMask()));
        dto.setPermissions(permissions);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(dto);
        this.mvc.perform(post("/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());

        ResourceRangePermissionWrapper wrapper = this.permissionService.getByResourceRange(rr1.getId());


        assertThat(wrapper)
                .extracting("resourceRange.id", "permissions")
                .containsExactlyInAnyOrder(rr1.getId(), new HashSet<Permission>() {{ add(BasePermission.READ); add(BasePermission.WRITE); }});
    }

    /**
     * 测试修改一条权限信息
     */
    @Test
    public void save_updatePermission() throws Exception {
        this.permissionService.insertPermission(this.rr1.getId(), BasePermission.READ);

        ResourceRangePermissionWrapperDTO dto = new ResourceRangePermissionWrapperDTO();
        dto.setResourceRangeId(rr1.getId());

        Set<PermissionDTO> permissions = new HashSet<>();
//        permissions.add(new PermissionDTO(BasePermission.READ.getMask()));
        permissions.add(new PermissionDTO(BasePermission.WRITE.getMask()));
        dto.setPermissions(permissions);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(dto);

        this.mvc.perform(post("/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());

//                .param("resourceRangeId", String.valueOf(rr1.getId()))
//                .param("permissions", "W,"));

        ResourceRangePermissionWrapper wrapper = this.permissionService.getByResourceRange(rr1.getId());

        assertThat(wrapper)
                .extracting("resourceRange.id", "permissions")
                .containsExactlyInAnyOrder(rr1.getId(), Collections.singleton(BasePermission.WRITE));

    }

    /**
     * 保存一条不存在的ResourceRange的权限
     */
    @Test
    public void save_noResourceRange() throws Exception {

        ResourceRangePermissionWrapperDTO dto = new ResourceRangePermissionWrapperDTO();
        dto.setResourceRangeId(0);

        Set<PermissionDTO> permissions = new HashSet<>();
//        permissions.add(new PermissionDTO(BasePermission.READ.getMask()));
        permissions.add(new PermissionDTO(BasePermission.WRITE.getMask()));
        dto.setPermissions(permissions);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(dto);

        this.mvc.perform(post("/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
//                .param("resourceRangeId", "0")
//                .param("permissions", "W,"));

    }
}