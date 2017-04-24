package pw.ewen.WLPT.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domains.PermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.services.PermissionService;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
    public void HaveResourceRangeAndRole() throws Exception{
        this.permissionService.insertPermission(this.rr1.getId(), this.role1.getId(), BasePermission.READ);
        this.mvc.perform(get("/permissions?resourceRangeId={resourceRangeId}&roleId={roleId}", rr1.getId(), role1.getId()))
                .andExpect(jsonPath("$.resourceRangeId", is(Math.toIntExact(rr1.getId()))))
                .andExpect(jsonPath("$.roleId", is(role1.getId())))
                .andExpect(jsonPath("$.permissionDTOs[*].mask", containsInAnyOrder(BasePermission.READ.getMask())));
    }

    /**
     * ResourceRange或者Role不存在的情况，应该返回空数组
     * @throws Exception
     */
    @Test
    public void NoRole() throws Exception {

        Role noRole = new Role("noRole", "noRoleName");
        this.permissionService.insertPermission(rr1.getId(), role1.getId(), BasePermission.READ);

        this.mvc.perform(get("/permissions?resourceRangeId={resourceRangeId}&roleId={roleId}", this.rr1.getId(), noRole.getId()))
                .andExpect(status().isNotFound());

    }

    @Test
    public void NoResourceRange() throws  Exception {

        this.permissionService.insertPermission(rr1.getId(), role1.getId(), BasePermission.READ);

        this.mvc.perform(get("/permissions?resourceRangeId={resourceRangeId}&roleId={roleId}", 9999, this.role1.getId()))
                .andExpect(status().isNotFound());

    }

    /**
     * 测试插入一条permission
     * @throws Exception
     */
    @Test
    public void save_insertPermission() throws Exception {
        this.mvc.perform(post("/permissions")
                    .param("resourceRangeId", String.valueOf(rr1.getId()))
                    .param("roleId", role1.getId())
                    .param("permissions", "R,W,"));
        PermissionWrapper wrapper = this.permissionService.getByResourceRangeAndRole(rr1.getId(), role1.getId());


        assertThat(wrapper)
                .extracting("resourceRange.id", "role.id", "permissions")
                .containsExactlyInAnyOrder(rr1.getId(), role1.getId(), new HashSet<Permission>() {{ add(BasePermission.READ); add(BasePermission.WRITE); }});
    }

    /**
     * 测试修改一条权限信息
     */
    @Test
    public void save_updatePermission() throws Exception {
        this.permissionService.insertPermission(this.rr1.getId(), this.role1.getId(), BasePermission.READ);

        this.mvc.perform(post("/permissions")
                .param("resourceRangeId", String.valueOf(rr1.getId()))
                .param("roleId", role1.getId())
                .param("permissions", "W,"));

        PermissionWrapper wrapper = this.permissionService.getByResourceRangeAndRole(rr1.getId(), role1.getId());

        assertThat(wrapper)
                .extracting("resourceRange.id", "role.id", "permissions")
                .containsExactlyInAnyOrder(rr1.getId(), role1.getId(), Collections.singleton(BasePermission.WRITE));

    }

    /**
     * 保存一条不存在的ResourceRange的权限
     * 抛出异常，此处不是NotFoundExcption 而是 NestedException，所以expected = Exception
     */
    @Test(expected = Exception.class)
    public void save_noPermission_noResourceRange() throws Exception {

        this.mvc.perform(post("/permissions")
                .param("resourceRangeId", "0")
                .param("roleId", role1.getId())
                .param("permissions", "W,"));

    }

    /**
     * 保存一条不存在的Role的权限
     */
    @Test(expected = Exception.class)
    public void save_noPermission_noRole() throws Exception {

        Role role = new Role("noRole", "roleName");

        this.mvc.perform(post("/permissions")
                .param("resourceRangeId", "0")
                .param("roleId", role.getId())
                .param("permissions", "W,"));

    }
}