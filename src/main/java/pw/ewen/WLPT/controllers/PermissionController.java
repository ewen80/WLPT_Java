package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.DTOs.PermissionWrapperDTO;
import pw.ewen.WLPT.domains.PermissionWrapper;
import pw.ewen.WLPT.domains.Resource;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.services.PermissionService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wen on 17-3-5.
 * 权限控制Controller
 */
@RestController
@RequestMapping(value = "/permissions")
public class PermissionController {

    private PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 通过ResourceRange和Role获取权限
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<PermissionWrapperDTO> getByResourceRangeAndRole(@RequestParam long resourceRangeId,
                                                                @RequestParam String roleId){
        List<PermissionWrapper> wrapperList = this.permissionService.getByResourceRangeAndRole(resourceRangeId, roleId);
        return wrapperList.stream()
                    .map( PermissionWrapperDTO::convertFromPermissionWrapper)
                    .collect(Collectors.toList());
    }

    /**
     * 保存权限
     * @param range
     * @param role
     * @param permissions
     */
    public void save(ResourceRange range, Role role, Permission[] permissions){
        this.permissionService.deleteAllPermissions(range, role);

        for(Permission p : permissions) {
            this.permissionService.insertPermission(range, role, p);
        }
    }
}
