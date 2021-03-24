package pw.ewen.WLPT.domains.DTOs.permissions;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import pw.ewen.WLPT.domains.dtoconvertors.DTOConvert;

/**
 * Created by wenliang on 17-4-24.
 */
public class PermissionDTO  {

    int mask;

    public PermissionDTO(int mask) {
        this.mask = mask;
    }

    public PermissionDTO() {
    }

    private static class PermissionConverter implements DTOConvert<PermissionDTO, Permission> {
        @Override
        public Permission doForward(PermissionDTO permissionDTO) {
            //系统支持的权限类型
            Permission[] allPermissions = {BasePermission.READ, BasePermission.WRITE};
            for(Permission p : allPermissions) {
                if(permissionDTO.getMask() == p.getMask()) {
                    return p;
                }
            }
            return null;
        }

        @Override
        public PermissionDTO doBackward(Permission permission) {
            PermissionDTO dto = new PermissionDTO(permission.getMask());
            return  dto;
        }
    }

    public Permission convertToPermission() {
        PermissionConverter converter = new PermissionConverter();
        return converter.doForward(this);
    }

    public static PermissionDTO convertFromPermission(Permission permission) {
        PermissionConverter converter = new PermissionConverter();
        return converter.doBackward(permission);
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }
}
