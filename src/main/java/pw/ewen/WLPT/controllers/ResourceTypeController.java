package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.specifications.ResourceTypeSpecificationBuilder;
import pw.ewen.WLPT.services.ResourceTypeService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by wen on 17-3-12.
 */
@RestController
@RequestMapping(value = "/resourcetypes")
public class ResourceTypeController {
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    public ResourceTypeController(ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
    }

    @Autowired
    private ResourceTypeService resourceTypeService;

    /**
     * 获取资源类型（分页，查询）
     * @param pageIndex 第几页
     * @param pageSize  每页多少条
     */
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<ResourceType> getResourcesWithPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                   @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
                                                   @RequestParam(value = "filter", defaultValue = "") String filter){
       return this.resourceTypeService.getResourcesWithPage(pageIndex, pageSize, filter);
    }

    /**
     * 获取一个资源类型
     * @param className 资源类全限定名
     */
    @RequestMapping(value="/{className}", method=RequestMethod.GET, produces="application/json")
    @PostFilter("hasPermission(filterObject, 'read')")
    public ResourceType findByClassName(@PathVariable("className") String className){
        return this.resourceTypeService.findByClassName(className);
    }

    /**
     * 保存
     * @param resourceType 资源类型
     */
    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    @PreFilter("hasPermission(targetObject, 'write')")
    public ResourceType save(@RequestBody ResourceType resourceType){
        return this.resourceTypeService.save(resourceType);
    }

    /**
     * 软删除
     */
    @RequestMapping(value = "/{classNames}", method=RequestMethod.DELETE, produces = "application/json")
    @PreAuthorize("hasPermission(getResourceTypesFromClassNames(#classNames), 'write')")
    public void delete(@PathVariable("classNames") String classNames){
        Collection<ResourceType> resourceTypes = this.getResourceTypesFromClassNames(classNames);
        this.resourceTypeService.delete(resourceTypes);
    }

    //获取ResourceTypes根据classNames
    private Collection<ResourceType> getResourceTypesFromClassNames(String classNames){
        String[] arrClassNames = classNames.split(",");
        ArrayList<ResourceType> arrResourceTypes = new ArrayList<>();
        for(String className : arrClassNames){
            ResourceType rt = this.resourceTypeRepository.findOne(className);
            arrResourceTypes.add(rt);
        }
        return arrResourceTypes;
    }

}
