package pw.ewen.WLPT.test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.PermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.services.PermissionService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wen on 17-3-5.
 * 资源权限操作测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
public class PermissionServiceTest {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MutableAclService aclService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResourceRangeRepository resourceRangeRepository;
    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    private Role testRole;
    private Role testRole1;

    private User testUser;
    private GrantedAuthoritySid testSid;

    private ResourceRange resourceRange;
    private ResourceRange resourceRange1;

    private ResourceType resourceType;


    @Before
    public  void setup(){
        testRole = new Role("role1", "role1");
        roleRepository.save(testRole);
        testRole1 = new Role("role2", "role2");
        roleRepository.save(testRole1);

        testUser = new User("user1", "user1", "user1", testRole);
        userRepository.save(testUser);

        testSid = new GrantedAuthoritySid(testRole.getId());

        resourceType = new ResourceType("className1","name","");
        resourceTypeRepository.save(resourceType);

        resourceRange = new ResourceRange("number = 200", testRole, resourceType);
        resourceRangeRepository.save(resourceRange);
        resourceRange1 = new ResourceRange("number > 1", testRole1, resourceType);
        resourceRangeRepository.save(resourceRange1);
    }

    /**
     * 测试是否能够通过给定ResourceRange和Role读取权限
     */
    @Test
    @WithMockUser(username = "admin", authorities = "admin")
    public void getByResourceRangeAndRole() {
        permissionService.insertPermission(resourceRange, testRole, BasePermission.READ);

        List<PermissionWrapper> wrappers = permissionService.getByResourceRangeAndRole(resourceRange.getId(), testRole.getId());
        assertThat(wrappers).hasSize(1);
        assertThat(wrappers.get(0))
                .extracting("resourceRange", "role", "permission")
                .containsExactly(resourceRange, testRole, BasePermission.READ);
    }

    /**
     * 测试添加权限规则,ResourceRange不存在
     */
    @Test
    @WithMockUser(username = "admin", authorities = {"admin"})
    public void insertPermissionWhenNotExistSameResourceRange(){

        permissionService.insertPermission(resourceRange, testRole, BasePermission.READ);

        Acl acl = aclService.readAclById(new ObjectIdentityImpl(resourceRange), Collections.singletonList(testSid));
        List<AccessControlEntry> aces = acl.getEntries();

        assertThat(aces).hasSize(1);

        AccessControlEntry ace = aces.get(0);
        assertThat(ace).extracting("Sid", "Permission", "Acl.ObjectIdentity").containsExactly(testSid, BasePermission.READ, new ObjectIdentityImpl(resourceRange));
    }

    /**
     * 添加权限规则，ResourceRange已经存在，相同的Permission不存在
     */
    @Test
    @WithMockUser(username = "admin", authorities = {"admin"})
    public void insertPermissionWhenExistSameResourceRangeAndDifferentPermission(){
        ResourceRange resourceRange1 = new ResourceRange("number = 100", testRole, resourceType);
        resourceRangeRepository.save(resourceRange1);

        permissionService.insertPermission(resourceRange, testRole, BasePermission.READ);
        permissionService.insertPermission(resourceRange1, testRole, BasePermission.WRITE);

        Acl acl = aclService.readAclById(new ObjectIdentityImpl(resourceRange));
        Boolean isGranted = acl.isGranted(Arrays.asList(BasePermission.WRITE,BasePermission.READ), Collections.singletonList(testSid), true);
        Assert.isTrue(isGranted);
    }

    /**
     * 测试添加权限规则（规则存在）
     * 规则如果存在抛出异常
     */
    @Test(expected = RuntimeException.class)
    @WithMockUser(username="admin", authorities = {"admin"})
    public  void insertPermissionWhenExist(){
        permissionService.insertPermission(resourceRange, testRole, BasePermission.READ);
        permissionService.insertPermission(resourceRange, testRole, BasePermission.READ);
    }

    /**
     * 测试删除权限规则（规则存在）
     */
    @Test
    @WithMockUser(username="admin", authorities = {"admin"})
    public void deletePermissionWhenExist(){
        permissionService.insertPermission(resourceRange, testRole, BasePermission.READ);

        Boolean result =  permissionService.deletePermission(resourceRange, testRole, BasePermission.READ);
        Assert.isTrue(result);
    }

    /**
     * 测试删除权限规则（规则不存在）
     */
    @Test
    @WithMockUser(username="admin", authorities = {"admin"})
    public void deletePermissionWhenNotExist(){
        Role role = new Role("role1", "role1");
//        GrantedAuthoritySid sid = new GrantedAuthoritySid(role.getId());
        ResourceRange rr = new ResourceRange("number = 200", role, this.resourceType);

        Boolean result = permissionService.deletePermission(rr, role, BasePermission.READ);
        Assert.isTrue(!result);
    }

    /**
     * 测试删除权限规则（规则不同）
     */
    @Test
    @WithMockUser(username="admin", authorities = {"admin"})
    public void deletePermissionWhenNotSame(){
        permissionService.insertPermission(resourceRange, testRole, BasePermission.READ);

        Boolean result = permissionService.deletePermission(resourceRange, testRole, BasePermission.WRITE);
        Assert.isTrue(!result);
    }

    /**
     * 测试删除全部权限功能
     */
    @Test
    @WithMockUser(username = "admin", authorities = "admin")
    public void deleteAllPermissions() {
        permissionService.insertPermission(resourceRange, testRole, BasePermission.READ);
        permissionService.insertPermission(resourceRange, testRole, BasePermission.WRITE);
        permissionService.insertPermission(resourceRange1, testRole1, BasePermission.READ);

        permissionService.deleteAllPermissions(resourceRange, testRole);

        List<PermissionWrapper> wrappers = permissionService.getByResourceRangeAndRole(resourceRange.getId(), testRole.getId());
        assertThat(wrappers).hasSize(0);
        wrappers = permissionService.getByResourceRangeAndRole(resourceRange1.getId(), testRole1.getId());
        assertThat(wrappers).hasSize(1);
    }
}
