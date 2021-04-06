package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.security.acls.model.Permission;
import pw.ewen.WLPT.domains.DTOs.permissions.PermissionDTO;
import pw.ewen.WLPT.domains.DTOs.permissions.ResourceRangePermissionWrapperDTO;
import pw.ewen.WLPT.domains.ResourceRangePermissionWrapper;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.services.ResourceRangeService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021-04-06
 */
public class ResourceRangePermissionWrapperDTOConvertor {

    // 无法转换返回null
    public ResourceRangePermissionWrapper toResourceRangePermissionWrapper(ResourceRangePermissionWrapperDTO dto, ResourceRangeService resourceRangeService) {
        ResourceRange range = resourceRangeService.findOne(dto.getResourceRangeId());
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

    public ResourceRangePermissionWrapperDTO toResourceRangePermissionWrapperDTO(ResourceRangePermissionWrapper wrapper) {
        ResourceRangePermissionWrapperDTO dto = new ResourceRangePermissionWrapperDTO();
        dto.setResourceRangeId(wrapper.getResourceRange().getId());
        Set<PermissionDTO> pDTO = wrapper.getPermissions().stream()
                .map( permission -> PermissionDTO.convertFromPermission(permission))
                .collect(Collectors.toSet());
        dto.setPermissions(pDTO);
        return dto;
    }
}
