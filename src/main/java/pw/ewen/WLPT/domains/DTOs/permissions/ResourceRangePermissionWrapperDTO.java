package pw.ewen.WLPT.domains.DTOs.permissions;

import org.springframework.security.acls.model.Permission;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.dtoconvertors.ResourceRangePermissionWrapperDTOConvertor;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.services.ResourceRangeService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by wen on 17-4-16.
 * ResourceRange的权限配置类
 */
public class ResourceRangePermissionWrapperDTO {

    private long resourceRangeId;
    private Set<PermissionDTO> permissions;

    /**
     * 转化DTO为PermissionWrapper对象
     */
    public ResourceRangePermissionWrapper convertToPermissionWrapper(ResourceRangeRepository resourceRangeRepository, ResourceRangeService resourceRangeService){
        ResourceRangePermissionWrapperDTOConvertor converter = new ResourceRangePermissionWrapperDTOConvertor();
        return converter.toResourceRangePermissionWrapper(this, resourceRangeService);
    }

    /**
     * 转化PermissionWrapper对象为DTO对象
     */
    public static ResourceRangePermissionWrapperDTO convertFromPermissionWrapper(ResourceRangePermissionWrapper wrapper){
        ResourceRangePermissionWrapperDTOConvertor converter = new ResourceRangePermissionWrapperDTOConvertor();
        return converter.toResourceRangePermissionWrapperDTO(wrapper);
    }

    public long getResourceRangeId() {
        return resourceRangeId;
    }

    public void setResourceRangeId(long resourceRangeId) {
        this.resourceRangeId = resourceRangeId;
    }

    public Set<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDTO> permissions) {
        this.permissions = permissions;
    }
}
