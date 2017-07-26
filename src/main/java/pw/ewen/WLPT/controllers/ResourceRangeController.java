package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.services.ResourceRangeService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wen on 17-4-3.
 */
@RestController
@RequestMapping(value = "/resourceranges")
public class ResourceRangeController {

    @Autowired
    private ResourceRangeService service;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @PostFilter("hasAuthority(@propertyConfig.getDefaultAdminRoleId()) || hasPermission(filterObject.convertToResourceRange(@roleRepository,@resourceTypeRepository), 'read')")
    public List<ResourceRangeDTO> getByResourceType(@RequestParam(value = "resourceId", defaultValue = "") Long resourceTypeId){
        return service.getByResourceType(resourceTypeId)
                .stream()
                .map( ResourceRangeDTO::convertFromResourceRange)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @PreAuthorize("hasAuthority(@propertyConfig.getDefaultAdminRoleId()) || hasPermission(#dto.convertToResourceRange(@roleRepository,@resourceTypeRepository), 'write')")
    public ResourceRangeDTO save(@RequestBody ResourceRangeDTO dto){
        ResourceRange range = dto.convertToResourceRange(this.roleRepository, this.resourceTypeRepository);
        range =  this.service.save(range);
        return ResourceRangeDTO.convertFromResourceRange(range);
    }

    @RequestMapping(method=RequestMethod.DELETE, produces = "application/json")
    @PreFilter("hasAuthority(@propertyConfig.getDefaultAdminRoleId()) || hasPermission(@resourceRangeService.findOne(filterObject), 'write')")
    public void delete(@RequestBody Collection<Long> ids){
        this.service.delete(ids);
    }

}
