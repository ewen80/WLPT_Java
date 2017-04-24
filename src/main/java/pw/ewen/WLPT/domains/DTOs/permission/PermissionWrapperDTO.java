package pw.ewen.WLPT.domains.DTOs.permission;

import org.springframework.security.acls.model.Permission;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.DTOs.DTOConvert;
import pw.ewen.WLPT.domains.PermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by wen on 17-4-16.
 */
public class PermissionWrapperDTO {

    private long resourceRangeId;
    private String roleId;
    private Set<PermissionDTO> permissionDTOs;

    //实现DTOConvert接口的内部类
    private static class PermissionWrapperConverter implements DTOConvert<PermissionWrapperDTO, PermissionWrapper> {

        private ResourceRangeRepository resourceRangeRepository;
        private RoleRepository roleRepository;

        private PermissionWrapper wrapper;

        public PermissionWrapperConverter() {
        }

        public PermissionWrapperConverter(ResourceRangeRepository resourceRangeRepository, RoleRepository roleRepository) {
            this.resourceRangeRepository = resourceRangeRepository;
            this.roleRepository = roleRepository;
        }

        @Override
        public PermissionWrapper doForward(PermissionWrapperDTO dto) {
            Assert.notNull(this.resourceRangeRepository);
            Assert.notNull(this.roleRepository);

            ResourceRange range = this.resourceRangeRepository.findOne(dto.getResourceRangeId());
            Role role = roleRepository.findOne(dto.getRoleId());
            if(range != null && role != null) {
                Set<Permission> permissions = new HashSet<>();
                for(PermissionDTO pDTO : dto.getPermissionDTOs()) {
                    permissions.add(pDTO.convertToPermission());
                }
                return  new PermissionWrapper(range, role, permissions);
            } else {
                return null;
            }
        }

        @Override
        public PermissionWrapperDTO doBackward(PermissionWrapper wrapper) {
            PermissionWrapperDTO dto = new PermissionWrapperDTO();
            dto.setResourceRangeId(wrapper.getResourceRange().getId());
            dto.setRoleId(wrapper.getRole().getId());
            Set<PermissionDTO> pDTO = wrapper.getPermissions().stream()
                                        .map( permission -> PermissionDTO.convertFromPermission(permission))
                                        .collect(Collectors.toSet());
            dto.setPermissionDTOs(pDTO);
            return dto;
        }
    }

    /**
     * 转化DTO为PermissionWrapper对象
     */
    public PermissionWrapper convertToPermissionWrapper(ResourceRangeRepository resourceRangeRepository, RoleRepository roleRepository){
        PermissionWrapperConverter converter = new PermissionWrapperConverter(resourceRangeRepository, roleRepository);
        return converter.doForward(this);
    }

    /**
     * 转化PermissionWrapper对象为DTO对象
     */
    public static PermissionWrapperDTO convertFromPermissionWrapper(PermissionWrapper wrapper){
        PermissionWrapperConverter converter = new PermissionWrapperConverter();
        return converter.doBackward(wrapper);
    }

    public long getResourceRangeId() {
        return resourceRangeId;
    }

    public void setResourceRangeId(long resourceRangeId) {
        this.resourceRangeId = resourceRangeId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Set<PermissionDTO> getPermissionDTOs() {
        return permissionDTOs;
    }

    public void setPermissionDTOs(Set<PermissionDTO> permissionDTOs) {
        this.permissionDTOs = permissionDTOs;
    }
}
