package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
//        return this.save(resourceType);
        return null;
    }

    public ResourceType findByClassName(String className){
        return this.resourceTypeRepository.findByClassName(className);
    }
}
