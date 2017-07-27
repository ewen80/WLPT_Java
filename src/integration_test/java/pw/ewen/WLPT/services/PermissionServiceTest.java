package pw.ewen.WLPT.services;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pw.ewen.WLPT.annotations.WithAdminUser;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
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
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@WithAdminUser
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
        testRole = roleRepository.save(testRole);
        testRole1 = new Role("role2", "role2");
        testRole1 = roleRepository.save(testRole1);

        testUser = new User("user1", "user1", "user1", testRole);
        testUser = userRepository.save(testUser);

//        testSid = new GrantedAuthoritySid(testRole.getRoleId());

        resourceType = new ResourceType("className1","name","");
        resourceType = resourceTypeRepository.save(resourceType);

        resourceRange = new ResourceRange("number == 200", testRole, resourceType);
        resourceRange = resourceRangeRepository.save(resourceRange);
        resourceRange1 = new ResourceRange("number > 1", testRole1, resourceType);
        resourceRange1 = resourceRangeRepository.save(resourceRange1);
    }
//
//    /**
//     * 测试是否能够通过给定ResourceRange读取权限
//     */
//    @Test
//    public void getByResourceRange() throws Exception {
//        permissionService.insertPermission(resourceRange.getId(), BasePermission.READ);
//
//        ResourceRangePermissionWrapper wrapper = permissionService.getByResourceRange(resourceRange.getId());
//        assertThat(wrapper.getPermissions()).hasSize(1);
//        assertThat(wrapper)
//                .extracting("resourceRange","permissions")
//                .containsExactly(resourceRange, Collections.singleton(BasePermission.READ));
//    }

//    /**
//     * 测试添加权限规则,ResourceRange不存在
//     */
//    @Test
//    public void insertPermissionWhenNotExistSameResourceRange() throws Exception {
//
//        permissionService.insertPermission(resourceRange.getId(), BasePermission.READ);
//
//        Acl acl = aclService.readAclById(new ObjectIdentityImpl(resourceRange), Collections.singletonList(testSid));
//        List<AccessControlEntry> aces = acl.getEntries();
//
//        assertThat(aces).hasSize(1);
//
//        AccessControlEntry ace = aces.get(0);
//        assertThat(ace).extracting("Sid", "Permission", "Acl.ObjectIdentity")
//                .containsExactly(testSid, BasePermission.READ, new ObjectIdentityImpl(resourceRange));
//    }
//
//    /**
//     * 添加权限规则，ResourceRange已经存在，相同的Permission不存在
//     */
//    @Test
//    public void insertPermissionWhenExistSameResourceRangeAndDifferentPermission() throws Exception {
//
//        permissionService.insertPermission(resourceRange.getId(), BasePermission.READ);
//        permissionService.insertPermission(resourceRange1.getId(), BasePermission.WRITE);
//
//        Acl acl = aclService.readAclById(new ObjectIdentityImpl(resourceRange));
//        Boolean isGranted = acl.isGranted(Arrays.asList(BasePermission.WRITE,BasePermission.READ), Collections.singletonList(testSid), true);
//        Assert.isTrue(isGranted);
//    }
//
//    /**
//     * 测试添加权限规则（规则存在）
//     * 规则如果存在抛出异常
//     */
//    @Test(expected = RuntimeException.class)
//    public  void insertPermissionWhenExist() throws Exception{
//        permissionService.insertPermission(resourceRange.getId(), BasePermission.READ);
//        permissionService.insertPermission(resourceRange.getId(), BasePermission.READ);
//    }
//
//    /**
//     * 测试删除权限规则（规则存在）
//     */
//    @Test
//    public void deletePermissionWhenExist() throws Exception{
//        permissionService.insertPermission(resourceRange.getId(), BasePermission.READ);
//
//        Boolean result =  permissionService.deletePermission(resourceRange.getId(), BasePermission.READ, true);
//        Assert.isTrue(result);
//    }
//
//    /**
//     * 测试删除权限规则（规则不存在）
//     */
//    @Test
//    public void deletePermission_PermissionNotExist() {
//        Boolean result = permissionService.deletePermission(this.resourceRange.getId(), BasePermission.READ, true);
//        Assert.isTrue(!result);
//    }
//
//    /**
//     * 测试删除权限规则（ResourceRange不存在）
//     */
//    @Test(expected = EntityNotFoundException.class)
//    public void deletePermission_NotExistResourceRange() {
//        permissionService.deletePermission(0, BasePermission.READ, true);
//
//    }

    @Test
    public void ttt(){
        Assert.isTrue(true);
    }
//
//    /**
//     * 测试删除权限规则（规则不同）
//     */
//    @Test
//    public void deletePermissionWhenNotSame() throws Exception{
//        permissionService.insertPermission(resourceRange.getId(), BasePermission.READ);
//
//        Boolean result = permissionService.deletePermission(resourceRange.getId(), BasePermission.WRITE, true);
//        Assert.isTrue(!result);
//    }
//
//    /**
//     * 测试删除全部权限功能
//     */
//    @Test
//    public void deleteResourceRangeAllPermissions() throws Exception {
//        permissionService.insertPermission(resourceRange.getId(), BasePermission.READ);
//        permissionService.insertPermission(resourceRange.getId(), BasePermission.WRITE);
//        permissionService.insertPermission(resourceRange1.getId(), BasePermission.READ);
//
//        permissionService.deleteResourceRangeAllPermissions(resourceRange.getId(), true);
//
//        ResourceRangePermissionWrapper wrapper = permissionService.getByResourceRange(resourceRange.getId());
//        assertThat(wrapper).isNull();
//        wrapper = permissionService.getByResourceRange(resourceRange1.getId());
//        assertThat(wrapper.getPermissions()).hasSize(1);
//    }
//
//    /**
//     * 插入不存在的ResourceRange规则
//     */
//    @Test(expected = EntityNotFoundException.class)
//    public void noResourceRangeFound() {
//        permissionService.insertPermission(0, BasePermission.READ);
//    }
}
