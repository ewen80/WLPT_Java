package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

/**
 * Created by wen on 17-7-11.
 */
@Service
public class ResourceTypeService {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    public ResourceType save(ResourceType resourceType){
        return this.resourceTypeRepository.save(resourceType);
    }

    /**
     * 保存ResourceType
     * @param resourceTypeClassName ResourceType的全限定名
     * @return 保存后的ResourceType
     */
    public ResourceType save(String resourceTypeClassName){
        ResourceType resourceType = new ResourceType(resourceTypeClassName);
        return this.save(resourceType);
    }

    public ResourceType findByClassName(String className){
        return this.resourceTypeRepository.findByClassName(className);
    }

    /**
     * 数据库中保存ResourceType信息
     * @param resourceTypeClassName 资源类的全限定名
     * @return
     */
    @Cacheable("resourceTypeInDBCache")
    public boolean initialResourceTypeInDB(String resourceTypeClassName){
        System.out.println("检查数据库中，cache无效");

        ResourceType resourceType = this.findByClassName(resourceTypeClassName);
        return resourceType != null;
    }
}
