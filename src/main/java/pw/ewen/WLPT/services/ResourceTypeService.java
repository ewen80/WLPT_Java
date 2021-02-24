package pw.ewen.WLPT.services;

import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

/**
 * created by wenliang on 2021-2-24
 */
@Service
public class ResourceTypeService {

    private ResourceTypeRepository resourceTypeRepository;

    public ResourceTypeService(ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
    }

    public ResourceType save(ResourceType resourceType) {
        return this.resourceTypeRepository.save(resourceType);
    }
}
