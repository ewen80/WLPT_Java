package pw.ewen.WLPT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domain.Resource;
import pw.ewen.WLPT.domain.ResourceRange;

import java.util.Arrays;
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
    public void savePermission(ResourceRange resourceRange, Sid sid, Permission permission){
        Assert.notNull(resourceRange);
        Assert.notNull(sid);
        Assert.notNull(permission);

        AccessControlEntry ace = findACE(resourceRange, sid, permission);
        ace.

        ObjectIdentityImpl oi = new ObjectIdentityImpl(resourceRange);
        //检测sid是否已存在此ObjectIdentity的Acl,如果存在则删除原有Acl
        try{
            MutableAcl existedAcl = (MutableAcl)aclService.readAclById(oi, Arrays.asList(sid));
            if(existedAcl != null){
                existedAcl.deleteAce();
            }
        }catch (NotFoundException e){
            //没有已存在的规则
        }
        MutableAcl mutableAcl = aclService.createAcl(oi);
        mutableAcl.setOwner(sid);
//          mutableAcl.setEntriesInheriting(false);
        mutableAcl.insertAce(0, permission, sid, true);
        aclService.updateAcl(mutableAcl);
    }

    /**
     * 删除权限规则
     */
    public void deletePermission(ResourceRange resourceRange){

    }

    /**
     * 根据指定条件找到ACL中的ACE
     * 返回ACE,如果没有找到则返回null
     */
    private AccessControlEntry findACE(ResourceRange resourceRange, Sid sid, Permission permission){
        Assert.notNull(resourceRange);
        Assert.notNull(sid);
        Assert.notNull(permission);

        ObjectIdentity oi = new ObjectIdentityImpl(resourceRange);
        Acl acl = aclService.readAclById(oi, Arrays.asList(sid));
        List<AccessControlEntry> aces =  acl.getEntries();
        for(AccessControlEntry ace : aces){
            if (ace.getSid().equals(sid) && ace.getPermission().equals(permission)) {
                return ace;
            }
        }
        return null;
    }
}
