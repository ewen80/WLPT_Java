package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.services.ResourceRangeService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wen on 17-4-3.
 */
@RestController
@RequestMapping(value = "/resourceranges")
public class ResourceRangeController {

   private ResourceRangeService service;
   private RoleRepository roleRepository;
   private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    public ResourceRangeController(ResourceRangeService service,
                                   RoleRepository roleRepository,
                                   ResourceTypeRepository resourceTypeRepository) {
       this.service = service;
       this.roleRepository = roleRepository;
       this.resourceTypeRepository = resourceTypeRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<ResourceRangeDTO> getByResourceType(@RequestParam(value = "resourceclassname", defaultValue = "") String resourceTypeClassName){
        return service.getByResourceType(resourceTypeClassName)
                .stream()
                .map( ResourceRangeDTO::convertFromResourceRange)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResourceRangeDTO save(@RequestBody ResourceRangeDTO dto){
        ResourceRange range = dto.convertToResourceRange(this.roleRepository, this.resourceTypeRepository);
        range =  this.service.save(range);
        return ResourceRangeDTO.convertFromResourceRange(range);
    }

    @RequestMapping(value = "/{resourceRangeIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("resourceRangeIds") String resourceRangeIds){
        String[] arrResourceRangeIds = resourceRangeIds.split(",");
        this.service.delete(arrResourceRangeIds);
    }

}
