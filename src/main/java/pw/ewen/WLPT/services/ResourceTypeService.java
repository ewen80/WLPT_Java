package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.Resource;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

/**
 * Created by wen on 17-7-11.
 */
@Service
public class ResourceTypeService {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    /**
     * 获取单个ResourceType
     * @param className 类全限定名
     * @return  ResourceType
     */
//    @PostFilter("hasPermission(filterObject, 'read')")
    public ResourceType getOne(String className){
        return this.resourceTypeRepository.findByClassName(className);
    }

//    @PreFilter("hasPermission(targetObject, 'write')")
    public ResourceType save(ResourceType resourceType){
        return this.resourceTypeRepository.save(resourceType);
    }

    /**
     * 保存ResourceType
     * @param resourceTypeClassName ResourceType的全限定名
     * @return 保存后的ResourceType
     */
    public ResourceType save(String resourceTypeClassName){
        return this.save(resourceTypeClassName, resourceTypeClassName);
    }

    /**
     * 保存ResourceType
     * @param resourceTypeName  资源类简称
     * @param resourceTypeClassName 资源类全限定名
     * @return
     */
    public ResourceType save(String resourceTypeName,String resourceTypeClassName){
        ResourceType resourceType = new ResourceType(resourceTypeClassName, resourceTypeName);
        return this.save(resourceType);
    }

//    @PostFilter("hasPermission(filterObject, 'write')")
    public ResourceType findByClassName(String className){
        return this.resourceTypeRepository.findByClassName(className);
    }
}
