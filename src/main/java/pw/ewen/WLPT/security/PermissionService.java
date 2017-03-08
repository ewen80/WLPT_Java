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

        if(isThisPermissionExist(resourceRange, sid, permission)){
            //当前已经存在此规则，抛出异常
            throw new AuthorizationException("不能插入已经存在的权限规则");
        }

        //当前没有此规则，可以插入
        MutableAcl mutableAcl = aclService.createAcl(new ObjectIdentityImpl(resourceRange));
        mutableAcl.setOwner(sid);
        mutableAcl.setEntriesInheriting(false);
        mutableAcl.insertAce(0, permission, sid, true);
        aclService.updateAcl(mutableAcl);
    }


    private boolean isThisPermissionExist(Object domainObject, Sid sid, Permission permission){
        ObjectIdentityImpl oi = new ObjectIdentityImpl(domainObject);
        try {
            Acl existedAcl = aclService.readAclById(oi);
            Boolean isGranted =  existedAcl.isGranted(Collections.singletonList(permission), Collections.singletonList(sid), true);
            if(isGranted) {
                //当前已经存在此规则，抛出异常
                return true;
            }
        }catch(NotFoundException e){
            //没有找到domainObject的acl
        }

        return false;
    }

    /**
     * 删除权限规则
     * @Return 如果删除一条ACE则返回true，如果没有找到对应ACE，即没有实际删除数据返回false
     */
    public boolean deletePermission(ResourceRange resourceRange, Sid sid, Permission permission){
        Assert.notNull(resourceRange);
        Assert.notNull(sid);
        Assert.notNull(permission);

        if(isThisPermissionExist(resourceRange, sid, permission)){
            //存在规则
            ObjectIdentityImpl oi = new ObjectIdentityImpl((resourceRange));
            MutableAcl mutableAcl = (MutableAcl)aclService.readAclById(oi, Collections.singletonList(sid));
            List<AccessControlEntry> aces = mutableAcl.getEntries();
            try{
                int matchedIndex = findAceIndex(aces, permission);
                mutableAcl.deleteAce(matchedIndex);
                return true;
            }catch(RuntimeException e){
                //没有找到规则
                return false;
            }
        }else{
            return false;
        }
    }

    private int findAceIndex(List<AccessControlEntry> aces, Permission permission) throws RuntimeException{
        int matchedAceIndex = -1;
        for(AccessControlEntry ace : aces){
            if(ace.getPermission().equals(permission)){
                matchedAceIndex = aces.indexOf(ace);
                break;
            }
        }
        if(matchedAceIndex != -1){
            return matchedAceIndex;
        }else{
            throw new RuntimeException("find no ACE in given ACEs");
        }
    }

}
