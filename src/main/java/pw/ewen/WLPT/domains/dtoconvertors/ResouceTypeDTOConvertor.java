package pw.ewen.WLPT.domains.dtoconvertors;

import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
import pw.ewen.WLPT.domains.DTOs.ResourceTypeDTO;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021/3/29
 */
public class ResouceTypeDTOConvertor {

    public ResourceType toResourceType(ResourceTypeDTO dto, RoleService roleService, ResourceTypeService resourceTypeService) {
        ResourceType resourceType = new ResourceType(dto.getClassName(), dto.getName(), dto.getDescription());
        if(dto.getResourceRanges() != null) {
            Set<ResourceRange> resourceRanges = dto.getResourceRanges().stream().map( (d) -> d.convertToResourceRange(roleService, resourceTypeService)).collect(Collectors.toSet());
            resourceType.setResourceRanges(resourceRanges);
        }
        return resourceType;
    }

    public ResourceTypeDTO toDTO(ResourceType resourceType) {
        ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO(resourceType.getClassName(), resourceType.getName());
        resourceTypeDTO.setDescription(resourceType.getDescription());
        Set<ResourceRange> resourceRanges = resourceType.getResourceRanges();
        Set<ResourceRangeDTO>  resourceRangeDTOS = new HashSet<>();
        resourceRanges.forEach( (r) -> resourceRangeDTOS.add(ResourceRangeDTO.convertFromResourceRange(r)));
        resourceTypeDTO.setResourceRanges(resourceRangeDTOS);
        return resourceTypeDTO;
    }
}
