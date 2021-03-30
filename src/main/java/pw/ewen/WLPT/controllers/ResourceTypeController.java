package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.DTOs.ResourceTypeDTO;
import pw.ewen.WLPT.domains.DTOs.RoleDTO;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.ResourceTypeService;
import pw.ewen.WLPT.services.RoleService;

/**
 * Created by wen on 17-3-12.
 */
@RestController
@RequestMapping(value = "/resourcetypes")
public class ResourceTypeController {
    private final ResourceTypeService resourceTypeService;
    private final RoleService roleService;

    @Autowired
    public ResourceTypeController(ResourceTypeService resourceTypeService,
                                  RoleService roleService) {
        this.resourceTypeService = resourceTypeService;
        this.roleService = roleService;
    }

    //转为DTO对象的内部辅助类
    static class ResourceTypeDTOConverter implements Converter<ResourceType, ResourceTypeDTO> {
        @Override
        public ResourceTypeDTO convert(ResourceType resourceType) {
            return  ResourceTypeDTO.convertFromResourceType(resourceType);
        }
    }

    /**
     * 获取资源类型（分页，查询）
     */
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<ResourceTypeDTO> getResourcesWithPage(PageInfo pageInfo){
        Page<ResourceType> resourceTypes;
        PageRequest pr = pageInfo.getPageRequest();

        if(pageInfo.getFilter().isEmpty()){
            resourceTypes =  this.resourceTypeService.findAll(pr);
        }else{
            resourceTypes =  this.resourceTypeService.findAll(pageInfo.getFilter(), pr);
        }

        return resourceTypes.map(new ResourceTypeDTOConverter());
    }

    /**
     * 获取一个资源类型
     * @param className 资源类全限定名
     */
    @RequestMapping(value="/{className}/", method=RequestMethod.GET, produces="application/json")
    public ResourceType getOne(@PathVariable("className") String className){
        return resourceTypeService.findOne(className);
    }

    /**
     * 保存
     * @param resourceTypeDTO 资源类型DTO
     */
    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public ResourceType save(@RequestBody ResourceTypeDTO resourceTypeDTO){
        ResourceType resourceType = ResourceTypeDTO.convertToResourceType(resourceTypeDTO, roleService, resourceTypeService);
        return this.resourceTypeService.save(resourceType);
    }

    /**
     * 软删除
     */
    @RequestMapping(value = "/{resourceTypes}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("resourceTypes") String resourceTypes){
        String[] arrClassNames = resourceTypes.split(",");
        this.resourceTypeService.delete(arrClassNames);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/check/{className}/")
    public boolean checkClassNameExist(@PathVariable("className") String className) {
        return resourceTypeService.findOne(className) != null;
    }
}
