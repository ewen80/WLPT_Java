package pw.ewen.WLPT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domain.Resource;
import pw.ewen.WLPT.domain.ResourceRange;
import pw.ewen.WLPT.exception.security.AuthorizationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wen on 17-3-5.
 * 权限操作服务类
 */
@Service
public class PermissionService {

    private final MutableAclService aclService;

    @Autowired
    public PermissionService(MutableAclService aclService) {
        this.aclService = aclService;
    }

    /**
     * 保存资源存取权限规则
     */
    public void insertPermission(ResourceRange resourceRange, Sid sid, Permission permission){
        Assert.notNull(resourceRange);
        Assert.notNull(sid);
        Assert.notNull(permission);

        ObjectIdentityImpl oi = new ObjectIdentityImpl(resourceRange);
        //检测sid是否已存在此ObjectIdentity的Acl,如果存在则删除原有Acl
        try {
            Acl existedAcl = aclService.readAclById(oi, Collections.singletonList(sid));
            //当前已经存在此规则，抛出异常
            throw new AuthorizationException("不能插入已经存在的权限规则");
        }catch(NotFoundException e){
            //当前没有此规则，可以插入
            MutableAcl mutableAcl = aclService.createAcl(oi);
            mutableAcl.setOwner(sid);
//          mutableAcl.setEntriesInheriting(false);
            mutableAcl.insertAce(0, permission, sid, true);
            aclService.updateAcl(mutableAcl);
        }
    }

    /**
     * 删除权限规则
     */
    public void deletePermission(ResourceRange resourceRange){

    }

}
