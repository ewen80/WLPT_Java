package pw.ewen.WLPT.domains.DTOs.permissions;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import pw.ewen.WLPT.domains.dtoconvertors.PermissionDTOConvertor;

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


    public Permission convertToPermission() {
        PermissionDTOConvertor converter = new PermissionDTOConvertor();
        return converter.toPermission(this);
    }

    public static PermissionDTO convertFromPermission(Permission permission) {
        PermissionDTOConvertor converter = new PermissionDTOConvertor();
        return converter.toDTO(permission);
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }
}
