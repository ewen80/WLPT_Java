package pw.ewen.WLPT.test.security;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.assertj.core.api.ListAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domain.entity.MyResource;
import pw.ewen.WLPT.domain.entity.MyResourceRange;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.exception.security.AuthorizationException;
import pw.ewen.WLPT.repository.MyResourceRangeRepository;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.repository.UserRepository;
import pw.ewen.WLPT.security.PermissionService;

import java.util.ArrayList;
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

    @Before
    public  void setup(){
        if(!testInitialed) {
            Role role1 = new Role("role1", "role1");
            roleRepository.save(role1);
            User user1 = new User("user1", "user1", "user1", role1);
            userRepository.save(user1);

            testInitialed = true;
        }
    }

    @After
    public  void clean(){
        if(testInitialed) {
            userRepository.delete("user1");
            roleRepository.delete("role1");

            testInitialed = false;
        }
    }
    /**
     * 测试添加权限规则（规则不存在）
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"admin"})
    public void insertPermissionWhenNotExist(){
        Role role = roleRepository.getOne("role1");
        GrantedAuthoritySid sid = new GrantedAuthoritySid(role.getID());
        MyResourceRange rr = new MyResourceRange("number = 200", role.getID());
        permissionService.insertPermission(rr, sid, BasePermission.READ);

        Acl acl = aclService.readAclById(new ObjectIdentityImpl(rr), Collections.singletonList(sid));
        List<AccessControlEntry> aces = acl.getEntries();

        assertThat(aces).hasSize(1);

        AccessControlEntry ace = aces.get(0);
        assertThat(ace).extracting("Sid", "Permission", "Acl.ObjectIdentity").containsExactly(sid, BasePermission.READ, new ObjectIdentityImpl(rr));
    }

    /**
     * 测试添加权限规则（规则存在）
     * 规则如果存在抛出异常
     */
    @Test
    @Transactional
    @WithMockUser(username="admin", authorities = {"admin"})
    public  void insertPermissionWhenExist(){
        Role role = new Role("role1", "role1");
        GrantedAuthoritySid sid = new GrantedAuthoritySid(role.getID());
        MyResourceRange rr = new MyResourceRange("number = 200", role.getID());
        permissionService.insertPermission(rr, sid, BasePermission.READ);

        try{
            permissionService.insertPermission(rr, sid, BasePermission.READ);
            fail("应该抛出AuthorizationException");
        }catch(AuthorizationException ae){ }
    }

    /**
     * 测试删除权限规则（规则存在）
     */
    @Test
    @Transactional
    @WithMockUser(username="admin", authorities = {"admin"})
    public void deletePermissionWhenExist(){
        Role role = new Role("role1", "role1");
        GrantedAuthoritySid sid = new GrantedAuthoritySid(role.getID());
        MyResourceRange rr = new MyResourceRange("number = 200", role.getID());
        permissionService.insertPermission(rr, sid, BasePermission.READ);


        Boolean result =  permissionService.deletePermission(rr, sid, BasePermission.READ);
        Assert.isTrue(result);
    }

    /**
     * 测试删除权限规则（规则不存在）
     */
    @Test
    @Transactional
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
    @Transactional
    @WithMockUser(username="admin", authorities = {"admin"})
    public void deletePermissionWhenNotSame(){
        Role role = new Role("role1", "role1");
        GrantedAuthoritySid sid = new GrantedAuthoritySid(role.getID());
        MyResourceRange rr = new MyResourceRange("number = 200", role.getID());

        permissionService.insertPermission(rr, sid, BasePermission.READ);

        Boolean result = permissionService.deletePermission(rr, sid, BasePermission.WRITE);
        Assert.isTrue(!result);
    }
}
