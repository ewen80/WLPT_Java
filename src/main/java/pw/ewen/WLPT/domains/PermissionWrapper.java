package pw.ewen.WLPT.domains;

import org.springframework.security.acls.model.Permission;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.Role;

/**
 * Created by wen on 17-4-16.
 * 权限包装类
 */
public class PermissionWrapper {

    private ResourceRange resourceRange;
    private Role role;
    private Permission permission;

    public PermissionWrapper(ResourceRange resourceRange, Role role, Permission permission) {
        this.resourceRange = resourceRange;
        this.role = role;
        this.permission = permission;
    }



    public ResourceRange getResourceRange() {
        return resourceRange;
    }

    public void setResourceRange(ResourceRange resourceRange) {
        this.resourceRange = resourceRange;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
