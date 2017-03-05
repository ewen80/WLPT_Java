package pw.ewen.WLPT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domain.Resource;
import pw.ewen.WLPT.domain.ResourceRange;

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
    public void savePermission(ResourceRange resourceRange, Sid sid, Permission permission){
        Assert.notNull(resourceRange);
        Assert.notNull(sid);
        Assert.notNull(permission);

        ObjectIdentityImpl oi = new ObjectIdentityImpl(resourceRange);
        MutableAcl acl = aclService.createAcl(oi);
        acl.setOwner(sid);
//        acl.setEntriesInheriting(false);
        acl.insertAce(0, permission, sid, true);

        aclService.updateAcl(acl);
    }
}
