package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.ResourceRangeSpecificationBuilder;

import java.util.List;

/**
 * Created by wenliang on 17-4-12.
 */
@Service
public class ResourceRangeService {

    private ResourceRangeRepository resourceRangeRepository;
    private RoleRepository roleRepository;
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    public ResourceRangeService(ResourceRangeRepository resourceRangeRepository, RoleRepository roleRepository, ResourceTypeRepository resourceTypeRepository) {
        this.resourceRangeRepository = resourceRangeRepository;
        this.roleRepository = roleRepository;
        this.resourceTypeRepository = resourceTypeRepository;
    }

    public List<ResourceRange> getByResourceType(String resourceTypeClassName){
        Assert.hasText(resourceTypeClassName);

        ResourceRangeSpecificationBuilder builder = new ResourceRangeSpecificationBuilder();
        return this.resourceRangeRepository.findAll(builder.build("resourceType.className:"+resourceTypeClassName));
    }

    public ResourceRange save(ResourceRange range){
        return this.resourceRangeRepository.save(range);
    }
}
