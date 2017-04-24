package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.DTOs.permission.PermissionWrapperDTO;
import pw.ewen.WLPT.domains.PermissionWrapper;
import pw.ewen.WLPT.exceptions.domain.PermissionNotFoundException;
import pw.ewen.WLPT.services.PermissionService;

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
    public PermissionWrapperDTO getByResourceRangeAndRole(@RequestParam long resourceRangeId, @RequestParam String roleId){
        PermissionWrapper wrapper = this.permissionService.getByResourceRangeAndRole(resourceRangeId, roleId);
        if(wrapper != null) {
            return PermissionWrapperDTO.convertFromPermissionWrapper(wrapper);
        } else {
            throw new PermissionNotFoundException();
        }
    }

    /**
     * 保存权限
     * @param resourceRangeId
     * @param roleId
     * @param permissions   逗号分割的权限字符串（读：R,写：W）
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public void save(@RequestParam long resourceRangeId,
                     @RequestParam String roleId,
                     @RequestParam String permissions)  {

        this.permissionService.deleteAllPermissions(resourceRangeId, roleId);

        String[] arrPermissions = permissions.split(",");
        for (String p : arrPermissions) {
            Permission permission = null;
            switch (p) {
                case "R":
                    permission = BasePermission.READ;
                    break;
                case "W":
                    permission = BasePermission.WRITE;
                    break;
            }
            this.permissionService.insertPermission(resourceRangeId, roleId, permission);
        }
    }
}
