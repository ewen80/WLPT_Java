package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

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

    /**
     * 获取资源类型（分页，查询）
     * @param pageIndex 第几页
     * @param pageSize  每页多少条
     */
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<ResourceType> getResourcesWithPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                   @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
                                                   @RequestParam(value = "filter", defaultValue = "") String filter){
        SearchSpecificationsBuilder<ResourceType> builder = new SearchSpecificationsBuilder<>();
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
        return resourceTypeRepository.findOne(className);
    }

    /**
     * 保存
     * @param resourceType 资源类型
     */
    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public ResourceType save(@RequestBody ResourceType resourceType){
        return this.resourceTypeRepository.save(resourceType);
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
