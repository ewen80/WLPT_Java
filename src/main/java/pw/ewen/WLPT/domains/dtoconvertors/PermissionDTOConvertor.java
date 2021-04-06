package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.security.acls.domain.BasePermission;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;

import org.springframework.security.acls.model.Permission;

/**
 * created by wenliang on 2021-04-06
 */
public class PermissionDTOConvertor {

    // 无法转换返回 null
    public Permission toPermission(PermissionDTO dto) {
        //系统支持的权限类型
        org.springframework.security.acls.model.Permission[] allPermissions = {BasePermission.READ, BasePermission.WRITE, BasePermission.ADMINISTRATION};
        for(org.springframework.security.acls.model.Permission p : allPermissions) {
            if(dto.getMask() == p.getMask()) {
                return p;
            }
        }
        return null;
    }

    public PermissionDTO toDTO(Permission permission) {
        PermissionDTO dto = new PermissionDTO(permission.getMask());
        return  dto;
    }
}
