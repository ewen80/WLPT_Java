package pw.ewen.WLPT.test.security;

import org.junit.After;
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
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domain.entity.MyResourceRange;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.exception.security.AuthorizationException;
import pw.ewen.WLPT.repository.MyResourceRangeRepository;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.repository.UserRepository;
import pw.ewen.WLPT.security.PermissionService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

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
    private MyResourceRangeRepository myResourceRangeRepository;

    //Class Test 数据是否准备好
    private boolean testInitialed = false;

    private Role testRole;
    private User testUser;
    private GrantedAuthoritySid testSid;
    private MyResourceRange myResourceRange;


    @Before
    public  void setup(){
        if(!testInitialed) {
            testRole = new Role("role1", "role1");
//            roleRepository.save(testRole);
            testUser = new User("user1", "user1", "user1", testRole);
//            userRepository.save(testUser);
            testSid = new GrantedAuthoritySid(testRole.getID());

            myResourceRange = new MyResourceRange("number = 200", testRole.getID());
            myResourceRangeRepository.save(myResourceRange);

            testInitialed = true;
        }
    }

//    @After
//    public  void clean(){
//        if(testInitialed) {
//            userRepository.delete("user1");
//            roleRepository.delete("role1");
//
//            ObjectIdentity oi = new ObjectIdentityImpl(myResourceRange);
//            aclService.deleteAcl(oi, false);
//            myResourceRangeRepository.delete(myResourceRange);
//
//            testInitialed = false;
//        }
//    }
    /**
     * 测试添加权限规则,ResourceRange不存在
     */
    @Test
    @WithMockUser(username = "admin", authorities = {"admin"})
    public void insertPermissionWhenNotExistSameResourceRange(){
        permissionService.insertPermission(myResourceRange, testSid, BasePermission.READ);

        Acl acl = aclService.readAclById(new ObjectIdentityImpl(myResourceRange), Collections.singletonList(testSid));
        List<AccessControlEntry> aces = acl.getEntries();

        assertThat(aces).hasSize(1);

        AccessControlEntry ace = aces.get(0);
        assertThat(ace).extracting("Sid", "Permission", "Acl.ObjectIdentity").containsExactly(testSid, BasePermission.READ, new ObjectIdentityImpl(myResourceRange));
    }

    /**
     * 添加权限规则，ResourceRange已经存在，相同的Permission不存在
     */
    @Test
    @WithMockUser(username = "admin", authorities = {"admin"})
    public void insertPermissionWhenExistSameResourceRangeAndDifferentPermission(){
        permissionService.insertPermission(myResourceRange, testSid, BasePermission.READ);
        permissionService.insertPermission(myResourceRange, testSid, BasePermission.WRITE);

        Acl acl = aclService.readAclById(new ObjectIdentityImpl(myResourceRange));
        Boolean isGranted = acl.isGranted(Arrays.asList(BasePermission.WRITE,BasePermission.READ), Collections.singletonList(testSid), true);
        Assert.isTrue(isGranted);
    }

    /**
     * 测试添加权限规则（规则存在）
     * 规则如果存在抛出异常
     */
    @Test
    @WithMockUser(username="admin", authorities = {"admin"})
    public  void insertPermissionWhenExist(){
        permissionService.insertPermission(myResourceRange, testSid, BasePermission.READ);

        try{
            permissionService.insertPermission(myResourceRange, testSid, BasePermission.READ);
            fail("应该抛出AuthorizationException");
        }catch(AuthorizationException ae){ }
    }

    /**
     * 测试删除权限规则（规则存在）
     */
    @Test
    @WithMockUser(username="admin", authorities = {"admin"})
    public void deletePermissionWhenExist(){
        permissionService.insertPermission(myResourceRange, testSid, BasePermission.READ);

        Boolean result =  permissionService.deletePermission(myResourceRange, testSid, BasePermission.READ);
        Assert.isTrue(result);
    }

    /**
     * 测试删除权限规则（规则不存在）
     */
    @Test
    @WithMockUser(username="admin", authorities = {"admin"})
    public void deletePermissionWhenNotExist(){
        Role role = new Role("role1", "role1");
        GrantedAuthoritySid sid = new GrantedAuthoritySid(role.getID());
        MyResourceRange rr = new MyResourceRange("number = 200", role.getID());

        Boolean result = permissionService.deletePermission(rr, sid, BasePermission.READ);
        Assert.isTrue(!result);
    }

    /**
     * 测试删除权限规则（规则不同）
     */
    @Test
    @WithMockUser(username="admin", authorities = {"admin"})
    public void deletePermissionWhenNotSame(){
        permissionService.insertPermission(myResourceRange, testSid, BasePermission.READ);

        Boolean result = permissionService.deletePermission(myResourceRange, testSid, BasePermission.WRITE);
        Assert.isTrue(!result);
    }
}
