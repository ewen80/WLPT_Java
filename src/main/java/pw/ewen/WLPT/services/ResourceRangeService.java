package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

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

    public ResourceRange findOne(long id) {
        return resourceRangeRepository.findOne(id);
    }

    public List<ResourceRange> findByResourceType(String resourceTypeClassName){
        Assert.hasText(resourceTypeClassName);

        SearchSpecificationsBuilder<ResourceRange> builder = new SearchSpecificationsBuilder<>();
        return this.resourceRangeRepository.findAll(builder.build("resourceType.className:" + resourceTypeClassName));
    }

    public List<ResourceRange> findAll(Specification<ResourceRange> spec) {
        return this.resourceRangeRepository.findAll(spec);
    }

    /**
     * 根据角色和资源类型返回资源范围，一个角色和一个资源最多只能匹配到一个资源范围
     * @param resourceTypeClassName 资源类名
     * @param roleId 角色id
     * @return 返回资源范围，如果没有匹配到则返回null
     */
    public ResourceRange findByResourceTypeAndRole(String resourceTypeClassName, String roleId) {
        SearchSpecificationsBuilder<ResourceRange> builder = new SearchSpecificationsBuilder<>();
        List<ResourceRange> resultList = this.resourceRangeRepository.findAll(builder.build("resourceType.className:" + resourceTypeClassName + ",role.id:" + roleId));
        if(resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;
    }

    public ResourceRange save(ResourceRange range){
        return this.resourceRangeRepository.save(range);
    }

    public void delete(String[] resourceRangeIds){
        for(String id : resourceRangeIds){
            this.resourceRangeRepository.delete(Long.parseLong(id));
        }
    }
}
