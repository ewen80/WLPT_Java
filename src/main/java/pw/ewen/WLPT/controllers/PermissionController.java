package pw.ewen.WLPT.controllers;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.DTOs.PermissionWrapperDTO;
import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
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
