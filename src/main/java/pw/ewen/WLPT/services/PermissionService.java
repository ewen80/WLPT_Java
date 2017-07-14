package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.exceptions.security.AuthorizationException;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * Created by wen on 17-3-5.
 * 权限操作服务类
 */
@Service
public class PermissionService {

    private final MutableAclService aclService;
    private ResourceRangeRepository resourceRangeRepository;
    private RoleRepository roleRepository;

    @Autowired
    public PermissionService(MutableAclService aclService,
                             ResourceRangeRepository resourceRangeRepository,
                             RoleRepository roleRepository) {
        this.aclService = aclService;
        this.resourceRangeRepository = resourceRangeRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * 通过ResourceRange和Role获取PermissionWrapper
     * @param resourceRangeId ResourceRange对象的id值
     * @return  如果ResourceRange和Role根据id值没有找到对应对象，或者没有对应权限则返回null
     */
    public ResourceRangePermissionWrapper getByResourceRange(long resourceRangeId) {
        ResourceRange range = this.resourceRangeRepository.findOne(resourceRangeId);
        if(range != null){
            Set<Permission> permissions = new HashSet<>();
            ObjectIdentityImpl oi = new ObjectIdentityImpl(range);
            Sid sid = new GrantedAuthoritySid(range.getRole().getRoleId());
            try {
                MutableAcl mutableAcl = (MutableAcl)aclService.readAclById(oi, Collections.singletonList(sid));
                List<AccessControlEntry> entries = mutableAcl.getEntries();
                for(AccessControlEntry entry : entries) {
                    permissions.add(entry.getPermission());
                }
                return new ResourceRangePermissionWrapper(range, permissions);
            } catch ( org.springframework.security.acls.model.NotFoundException e) {
                //aclService.readAclById 没有找到Acl
            }
        }
        return null;
    }

    /**
     * 新增资源存取权限规则
     */
    public void insertPermission(long resourceRangeId, Permission permission) throws AuthorizationException, EntityNotFoundException {
        Assert.notNull(permission);

        MutableAcl mutableAcl;
        ResourceRange resourceRange = this.resourceRangeRepository.findOne(resourceRangeId);
        if(resourceRange != null) {
            Sid sid = new GrantedAuthoritySid(resourceRange.getRole().getRoleId());
            if(isThisResourceRangeExist(resourceRange)){
                if(isThisPermissionExist(resourceRange, sid, permission)){
                    //当前已经存在此规则，抛出异常
                    throw new AuthorizationException("不能插入已经存在的权限规则");
                }else{
                    //存在相同的ResourceRange
                    mutableAcl = (MutableAcl)aclService.readAclById(new ObjectIdentityImpl(resourceRange));
                }
            }else{
                //当前没有此规则，可以插入
                mutableAcl = aclService.createAcl(new ObjectIdentityImpl(resourceRange));
            }
            mutableAcl.setOwner(sid);
            mutableAcl.setEntriesInheriting(false);
            mutableAcl.insertAce(0, permission, sid, true);
            aclService.updateAcl(mutableAcl);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * 新增资源存取权限规则
     */
    public void insertPermissions(long resourceRangeId, Collection<Permission> permissions) throws AuthorizationException, EntityNotFoundException {
        for(Permission p: permissions){
            this.insertPermission(resourceRangeId, p);
        }
    }

    /**
     * 删除权限规则
     * @Return 如果删除一条ACE则返回true，如果没有找到对应ACE，即没有实际删除数据返回false
     */
    public boolean deletePermission(long resourceRangeId, Permission permission) {
        Assert.notNull(permission);

        MutableAcl mutableAcl;
        ResourceRange resourceRange = this.resourceRangeRepository.findOne(resourceRangeId);
        if(resourceRange != null) {
            Sid sid = new GrantedAuthoritySid(resourceRange.getRole().getRoleId());
            if(isThisPermissionExist(resourceRange, sid, permission)){
                //存在规则
                ObjectIdentityImpl oi = new ObjectIdentityImpl(resourceRange);
                mutableAcl = (MutableAcl)aclService.readAclById(oi, Collections.singletonList(sid));
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
        } else {
            throw new EntityNotFoundException();
        }

    }

    /**
     * 删除ResourceRange和Role的所有权限
     * @param resourceRangeId
     *
     */
    public void deleteResourceRangeAllPermissions(long resourceRangeId) {
        ResourceRangePermissionWrapper permissionWrapper = this.getByResourceRange(resourceRangeId);

        if(permissionWrapper != null) {
            for(Permission p : permissionWrapper.getPermissions()) {
                this.deletePermission(resourceRangeId, p);
            }
        }
    }

    //ACL中该ResourceRange是否存在记录
    private boolean isThisResourceRangeExist(ResourceRange resourceRange){
        ObjectIdentityImpl oi = new ObjectIdentityImpl(resourceRange);
        try{
            Acl existedAcl = aclService.readAclById(oi);
            return true;
        } catch (org.springframework.security.acls.model.NotFoundException e){
            return false;
        }
    }

    //ACL中是否有对应的ResourceRange的对应的permission权限
    private boolean isThisPermissionExist(ResourceRange resourceRange, Sid sid, Permission permission){
        if(isThisResourceRangeExist(resourceRange)){
            ObjectIdentityImpl oi = new ObjectIdentityImpl(resourceRange);
            try{
                Acl existedAcl = aclService.readAclById(oi);
                Boolean isGranted =  existedAcl.isGranted(Collections.singletonList(permission), Collections.singletonList(sid), true);
                if(isGranted) {
                    //当前已经存在此规则
                    return true;
                }
            }catch(org.springframework.security.acls.model.NotFoundException e){
                return false;
            }
        }else{
            return false;
        }
        return false;
    }



    private int findAceIndex(List<AccessControlEntry> aces, Permission permission){
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
