package pw.ewen.WLPT.domains;

import org.springframework.security.acls.model.Permission;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.Role;

import java.util.Set;

/**
 * Created by wen on 17-4-16.
 * 权限包装类
 */
public class PermissionWrapper {

    private ResourceRange resourceRange;
    private Role role;
    private Set<Permission> permissions;

    public PermissionWrapper(ResourceRange resourceRange, Role role, Set<Permission> permissions) {
        this.resourceRange = resourceRange;
        this.role = role;
        this.permissions = permissions;
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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
