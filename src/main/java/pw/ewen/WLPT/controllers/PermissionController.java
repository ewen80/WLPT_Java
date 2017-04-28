package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.permission.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.permission.ResourceRangePermissionWrapperDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.exceptions.domain.PermissionNotFoundException;
import pw.ewen.WLPT.services.PermissionService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * Created by wen on 17-3-5.
 * 权限控制Controller
 */
@RestController
@RequestMapping(value = "/permissions")
public class PermissionController {

    private PermissionService permissionService;

    public static final Permission[] SUPPORT_PERMISSIONS = { BasePermission.READ, BasePermission.WRITE };

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**获取权限
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResourceRangePermissionWrapperDTO getByResourceRange(@RequestParam long resourceRangeId){
        ResourceRangePermissionWrapper wrapper = this.permissionService.getByResourceRange(resourceRangeId);
        if(wrapper != null) {
            return ResourceRangePermissionWrapperDTO.convertFromPermissionWrapper(wrapper);
        } else {
            throw new PermissionNotFoundException();
        }
    }

    public Set<ResourceRangePermissionWrapper> getByResourceRanges() {

    }

    /**
     * 保存权限
     * @return int  插入的权限记录数
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @Transactional
    public int save(@RequestBody ResourceRangePermissionWrapperDTO wrapperDTO) {

        this.permissionService.deleteResourceRangeAllPermissions(wrapperDTO.getResourceRangeId());

        int insertNumber = 0;

        for (PermissionDTO pDTO : wrapperDTO.getPermissions()) {
            Optional<Permission> permission = Arrays.stream(SUPPORT_PERMISSIONS)
                    .filter(pm -> pm.getMask() == pDTO.getMask())
                    .findFirst();
            if(permission.isPresent()) {
                try {
                    this.permissionService.insertPermission(wrapperDTO.getResourceRangeId(), permission.get());
                    insertNumber++;
                } catch (Exception e ) {

                }
            }
        }
        return insertNumber;
    }
}
