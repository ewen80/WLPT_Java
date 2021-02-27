package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

/**
 * created by wenliang on 20210226
 */
@Service
public class ResourceTypeService {

    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    public ResourceTypeService(ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
    }

    public ResourceType save(ResourceType resourceType) {
        return this.resourceTypeRepository.save(resourceType);
    }
}
