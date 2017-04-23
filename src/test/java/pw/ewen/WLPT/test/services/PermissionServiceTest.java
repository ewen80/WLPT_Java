package pw.ewen.WLPT.test.services;

import javassist.NotFoundException;
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

import javax.persistence.EntityNotFoundException;
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
@WithMockUser(username="admin", authorities = {"admin"})
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
    public void getByResourceRangeAndRole() throws Exception {
        permissionService.insertPermission(resourceRange.getId(), testRole.getId(), BasePermission.READ);

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
    public void insertPermissionWhenNotExistSameResourceRange() throws Exception {

        permissionService.insertPermission(resourceRange.getId(), testRole.getId(), BasePermission.READ);

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
    public void insertPermissionWhenExistSameResourceRangeAndDifferentPermission() throws Exception {
        ResourceRange resourceRange1 = new ResourceRange("number = 100", testRole, resourceType);
        resourceRangeRepository.save(resourceRange1);

        permissionService.insertPermission(resourceRange.getId(), testRole.getId(), BasePermission.READ);
        permissionService.insertPermission(resourceRange1.getId(), testRole.getId(), BasePermission.WRITE);

        Acl acl = aclService.readAclById(new ObjectIdentityImpl(resourceRange));
        Boolean isGranted = acl.isGranted(Arrays.asList(BasePermission.WRITE,BasePermission.READ), Collections.singletonList(testSid), true);
        Assert.isTrue(isGranted);
    }

    /**
     * 测试添加权限规则（规则存在）
     * 规则如果存在抛出异常
     */
    @Test(expected = RuntimeException.class)
    public  void insertPermissionWhenExist() throws Exception{
        permissionService.insertPermission(resourceRange.getId(), testRole.getId(), BasePermission.READ);
        permissionService.insertPermission(resourceRange.getId(), testRole.getId(), BasePermission.READ);
    }

    /**
     * 测试删除权限规则（规则存在）
     */
    @Test
    public void deletePermissionWhenExist() throws Exception{
        permissionService.insertPermission(resourceRange.getId(), testRole.getId(), BasePermission.READ);

        Boolean result =  permissionService.deletePermission(resourceRange.getId(), testRole.getId(), BasePermission.READ);
        Assert.isTrue(result);
    }

    /**
     * 测试删除权限规则（规则不存在）
     */
    @Test
    public void deletePermission_PermissionNotExist() {
        Boolean result = permissionService.deletePermission(this.resourceRange.getId(), this.testRole.getId(), BasePermission.READ);
        Assert.isTrue(!result);
    }

    /**
     * 测试删除权限规则（ResourceRange或者Role不存在）
     */
    @Test(expected = EntityNotFoundException.class)
    public void deletePermission_NotExistResourceRange_OR_NotExistRole() {
        permissionService.deletePermission(0, this.testRole.getId(), BasePermission.READ);

    }

    /**
     * 测试删除权限规则（规则不同）
     */
    @Test
    public void deletePermissionWhenNotSame() throws Exception{
        permissionService.insertPermission(resourceRange.getId(), testRole.getId(), BasePermission.READ);

        Boolean result = permissionService.deletePermission(resourceRange.getId(), testRole.getId(), BasePermission.WRITE);
        Assert.isTrue(!result);
    }

    /**
     * 测试删除全部权限功能
     */
    @Test
    public void deleteAllPermissions() throws Exception {
        permissionService.insertPermission(resourceRange.getId(), testRole.getId(), BasePermission.READ);
        permissionService.insertPermission(resourceRange.getId(), testRole.getId(), BasePermission.WRITE);
        permissionService.insertPermission(resourceRange1.getId(), testRole1.getId(), BasePermission.READ);

        permissionService.deleteAllPermissions(resourceRange.getId(), testRole.getId());

        List<PermissionWrapper> wrappers = permissionService.getByResourceRangeAndRole(resourceRange.getId(), testRole.getId());
        assertThat(wrappers).hasSize(0);
        wrappers = permissionService.getByResourceRangeAndRole(resourceRange1.getId(), testRole1.getId());
        assertThat(wrappers).hasSize(1);
    }

    @Test(expected = javax.persistence.EntityNotFoundException.class)
    public void noResourceRangeFound() {
        permissionService.insertPermission(0, testRole.getId(), BasePermission.READ);
    }
}
