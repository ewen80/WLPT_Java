package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.ResourceRangeDTO;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.services.ResourceRangeService;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wen on 17-4-3.
 */
@RestController
@RequestMapping(value = "/resourceranges")
public class ResourceRangeController {

   private final ResourceRangeService resourceRangeService;
   private final RoleService roleService;
   private final ResourceTypeService resourceTypeService;

    @Autowired
    public ResourceRangeController(ResourceRangeService resourceRangeService,
                                   RoleService roleService,
                                   ResourceTypeService resourceTypeService) {
       this.resourceRangeService = resourceRangeService;
       this.roleService = roleService;
       this.resourceTypeService = resourceTypeService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{resourceClassName}", produces = "application/json")
    public List<ResourceRangeDTO> getByResourceType(@PathVariable(value = "resourceClassName") String resourceTypeClassName){
        return resourceRangeService.findByResourceType(resourceTypeClassName)
                .stream()
                .map( ResourceRangeDTO::convertFromResourceRange)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResourceRangeDTO> getByResourceTypeAndRole(@RequestParam(value = "resourceClassName") String resourceTypeClassName, @RequestParam(value = "roleId") String roleId) {
        ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(resourceTypeClassName, roleId);
        return range == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(ResourceRangeDTO.convertFromResourceRange(range), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkexist", produces = "application/json")
    public boolean checkResourceRangeExist(@RequestParam(value = "resourceClassName") String resourceTypeClassName, @RequestParam(value = "roleId") String roleId) {
        ResourceRange range = this.resourceRangeService.findByResourceTypeAndRole(resourceTypeClassName, roleId);
        return range != null;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResourceRangeDTO save(@RequestBody ResourceRangeDTO dto){
        ResourceRange range = dto.convertToResourceRange(this.roleService, this.resourceTypeService);
        range =  this.resourceRangeService.save(range);
        return ResourceRangeDTO.convertFromResourceRange(range);
    }

    @RequestMapping(value = "/{resourceRangeIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("resourceRangeIds") String resourceRangeIds){
        String[] arrResourceRangeIds = resourceRangeIds.split(",");
        this.resourceRangeService.delete(arrResourceRangeIds);
    }

}
