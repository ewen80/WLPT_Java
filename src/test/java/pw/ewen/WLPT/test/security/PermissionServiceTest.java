package pw.ewen.WLPT.test.security;

import org.assertj.core.api.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.AccessControlEntryImpl;
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
import pw.ewen.WLPT.domain.entity.MyResource;
import pw.ewen.WLPT.domain.entity.MyResourceRange;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.exception.security.AuthorizationException;
import pw.ewen.WLPT.repository.MyResourceRangeRepository;
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
    private MyResourceRangeRepository myResourceRangeRepository;

    /**
     * 测试添加权限规则（规则不存在）
     */
//    @Test
//    @Transactional
//    @WithMockUser(username = "admin", authorities = {"admin"})
//    public void insertPermissionWhenNotExist(){
//        Role role = new Role("role1", "role1");
//        GrantedAuthoritySid sid = new GrantedAuthoritySid(role.getID());
//        MyResourceRange rr = new MyResourceRange(199, "number = 200", role.getID());
//        permissionService.insertPermission(rr, sid, BasePermission.READ);
//
//        Acl acl = aclService.readAclById(new ObjectIdentityImpl(rr), Collections.singletonList(sid));
//        List<AccessControlEntry> aces = acl.getEntries();
//
//        assertThat(aces).hasSize(1);
//
//        AccessControlEntry ace = aces.get(0);
//        assertThat(ace).extracting("Sid", "Permission", "Acl.ObjectIdentity").containsExactly(sid, BasePermission.READ, new ObjectIdentityImpl(rr));
//    }

    /**
     * 测试添加权限规则（规则存在）
     * 规则如果存在抛出异常
     */
//    @Test
//    @Transactional
//    @WithMockUser(username="user1", authorities = {"admin"})
//    public  void savePermissionWhenExist(){
//        Role role = new Role("role1", "role1");
//        GrantedAuthoritySid sid = new GrantedAuthoritySid(role.getID());
//        MyResourceRange rr = new MyResourceRange(101, "number = 200", role.getID());
//        try {
//            permissionService.insertPermission(rr, sid, BasePermission.READ);
//            fail("应该抛出AuthorizationException");
//        }catch(AuthorizationException e){
//
//        }
//
//    }

    /**
     * 测试删除权限规则（规则存在）
     */
//    @Test
//    @Transactional
//    @WithMockUser(username = "guest")
//    public void deletePermissionWhenExist(){
//        MyResourceRange rr = myResourceRangeRepository.getOne(101L);
//        Assert.notNull(rr);
//
//        Role role = new Role("role1", "role1");
//        GrantedAuthoritySid sid = new GrantedAuthoritySid(role.getID());
//        permissionService.deletePermission(rr, );
//    }

    /**
     * 测试删除权限规则（规则不存在）
     */
    public void deletePermissionWhenNotExist(){

    }
}
