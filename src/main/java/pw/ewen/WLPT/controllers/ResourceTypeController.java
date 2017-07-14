package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.specifications.ResourceTypeSpecificationBuilder;
import pw.ewen.WLPT.services.ResourceTypeService;

/**
 * Created by wen on 17-3-12.
 * TODO:移植逻辑到services层
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
        ResourceTypeSpecificationBuilder builder = new ResourceTypeSpecificationBuilder();
        if(filter.isEmpty()){
            return resourceTypeRepository.findAll(new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
        }else{
            return resourceTypeRepository.findAll(builder.build(filter), new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
        }

    }

    /**
     * 获取一个资源类型
     * @param className 资源类全限定名
     */
    @RequestMapping(value="/{className}", method=RequestMethod.GET, produces="application/json")
    public ResourceType getOne(@PathVariable("className") String className){
        return this.resourceTypeService.getOne(className);
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
        //TODO:检查该资源当前是否有效，只能软删除
        for(String className : arrClassNames){
            ResourceType rt = this.resourceTypeRepository.findOne(className);
            if(rt != null ){
                rt.setDeleted(true);
                this.resourceTypeRepository.save(rt);
            }
        }

    }
}
