package pw.ewen.WLPT.domains.DTOs;

import pw.ewen.WLPT.domains.dtoconvertors.ResouceTypeDTOConvertor;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

import java.util.HashSet;
import java.util.Set;

/**
 * created by wenliang on 2021/3/29
 */
public class ResourceTypeDTO {

    private String className;
    private String name;
    private String description;
//    private boolean deleted = false;
    private Set<ResourceRangeDTO> resourceRanges = new HashSet<>();

    public ResourceTypeDTO() {
    }

    public ResourceTypeDTO(String className, String name) {
        this.className = className;
        this.name = name;
//        this.deleted = deleted;
    }

    public static ResourceTypeDTO convertFromResourceType(ResourceType resourceType) {
        ResouceTypeDTOConvertor resouceTypeDTOConvertor = new ResouceTypeDTOConvertor();
        return resouceTypeDTOConvertor.toDTO(resourceType);
    }

    public static ResourceType convertToResourceType(ResourceTypeDTO resourceTypeDTO, RoleService roleService, ResourceTypeService resourceTypeService) {
        ResouceTypeDTOConvertor resouceTypeDTOConvertor = new ResouceTypeDTOConvertor();
        return resouceTypeDTOConvertor.toResourceType(resourceTypeDTO, roleService, resourceTypeService);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ResourceRangeDTO> getResourceRanges() {
        return resourceRanges;
    }

    public void setResourceRanges(Set<ResourceRangeDTO> resourceRanges) {
        this.resourceRanges = resourceRanges;
    }
}
