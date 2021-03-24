package pw.ewen.WLPT.domains.DTOs.permissions;

import org.springframework.security.acls.model.Permission;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.dtoconvertors.DTOConvert;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;

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

    //实现DTOConvert接口的内部类
    private static class PermissionWrapperConverter implements DTOConvert<ResourceRangePermissionWrapperDTO, ResourceRangePermissionWrapper> {

        private ResourceRangeRepository resourceRangeRepository;
        private ResourceRangePermissionWrapper wrapper;

        public PermissionWrapperConverter() {
        }

        public PermissionWrapperConverter(ResourceRangeRepository resourceRangeRepository) {
            this.resourceRangeRepository = resourceRangeRepository;
        }

        @Override
        public ResourceRangePermissionWrapper doForward(ResourceRangePermissionWrapperDTO dto) {
            Assert.notNull(this.resourceRangeRepository);

            ResourceRange range = this.resourceRangeRepository.findOne(dto.getResourceRangeId());
            if(range != null) {
                Set<Permission> permissions = new HashSet<>();
                for(PermissionDTO pDTO : dto.getPermissions()) {
                    permissions.add(pDTO.convertToPermission());
                }
                return  new ResourceRangePermissionWrapper(range, permissions);
            } else {
                return null;
            }
        }

        @Override
        public ResourceRangePermissionWrapperDTO doBackward(ResourceRangePermissionWrapper wrapper) {
            ResourceRangePermissionWrapperDTO dto = new ResourceRangePermissionWrapperDTO();
            dto.setResourceRangeId(wrapper.getResourceRange().getId());
            Set<PermissionDTO> pDTO = wrapper.getPermissions().stream()
                                        .map( permission -> PermissionDTO.convertFromPermission(permission))
                                        .collect(Collectors.toSet());
            dto.setPermissions(pDTO);
            return dto;
        }
    }

    /**
     * 转化DTO为PermissionWrapper对象
     */
    public ResourceRangePermissionWrapper convertToPermissionWrapper(ResourceRangeRepository resourceRangeRepository){
        PermissionWrapperConverter converter = new PermissionWrapperConverter(resourceRangeRepository);
        return converter.doForward(this);
    }

    /**
     * 转化PermissionWrapper对象为DTO对象
     */
    public static ResourceRangePermissionWrapperDTO convertFromPermissionWrapper(ResourceRangePermissionWrapper wrapper){
        PermissionWrapperConverter converter = new PermissionWrapperConverter();
        return converter.doBackward(wrapper);
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
