package pw.ewen.WLPT.domains;

import org.springframework.security.acls.model.Permission;
import pw.ewen.WLPT.domains.entities.ResourceRange;

import java.util.Set;

/**
 * Created by wen on 17-4-16.
 * 权限包装类
 */
public class ResourceRangePermissionWrapper {

    private ResourceRange resourceRange;
    private Set<Permission> permissions;

    public ResourceRangePermissionWrapper(ResourceRange resourceRange, Set<Permission> permissions) {
        this.resourceRange = resourceRange;
        this.permissions = permissions;
    }



    public ResourceRange getResourceRange() {
        return resourceRange;
    }

    public void setResourceRange(ResourceRange resourceRange) {
        this.resourceRange = resourceRange;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
