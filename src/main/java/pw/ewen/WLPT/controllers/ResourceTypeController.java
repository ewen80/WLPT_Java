package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.controllers.utils.PageInfo;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;
import pw.ewen.WLPT.services.ResourceTypeService;

/**
 * Created by wen on 17-3-12.
 */
@RestController
@RequestMapping(value = "/resourcetypes")
public class ResourceTypeController {
    private final ResourceTypeService resourceTypeService;

    @Autowired
    public ResourceTypeController(ResourceTypeService resourceTypeService) {
        this.resourceTypeService = resourceTypeService;
    }

    /**
     * 获取资源类型（分页，查询）
     */
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<ResourceType> getResourcesWithPage(PageInfo pageInfo){
        Page<ResourceType> resourceTypes;
        PageRequest pr = pageInfo.getPageRequest();

        if(pageInfo.getFilter().isEmpty()){
            resourceTypes =  this.resourceTypeService.findAll(pr);
        }else{
            resourceTypes =  this.resourceTypeService.findAll(pageInfo.getFilter(), pr);
        }
        return resourceTypes;
    }

    /**
     * 获取一个资源类型
     * @param className 资源类全限定名
     */
    @RequestMapping(value="/{className}", method=RequestMethod.GET, produces="application/json")
    public ResourceType getOne(@PathVariable("className") String className){
        return resourceTypeService.findOne(className);
    }

    /**
     * 保存
     * @param resourceType 资源类型
     */
    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public ResourceType save(@RequestBody ResourceType resourceType){
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
}
