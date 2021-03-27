package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.exceptions.domain.DeleteResourceTypeException;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecificationsBuilder;

/**
 * created by wenliang on 20210226
 */
@Service
public class ResourceTypeService {

    private final ResourceTypeRepository resourceTypeRepository;

    @Autowired
    public ResourceTypeService(ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
    }

    public ResourceType save(ResourceType resourceType) {
        return this.resourceTypeRepository.save(resourceType);
    }

    public Page<ResourceType> findAll(String filter, PageRequest pr) {
        // 默认过滤已删除资源类型
        SearchSpecificationsBuilder<ResourceType> builder = new SearchSpecificationsBuilder<>();
        String filterStr = "deleted:false," + filter;
        return this.resourceTypeRepository.findAll(builder.build(filterStr), pr);
    }

    public Page<ResourceType> findAll(PageRequest pr) {
        // 默认过滤已删除资源类型
        SearchSpecificationsBuilder<ResourceType> builder = new SearchSpecificationsBuilder<>();
        return this.resourceTypeRepository.findAll(builder.build("deleted:false"), pr);
    }

    public ResourceType findOne(String className) {
        return this.resourceTypeRepository.findOne(className);
    }

    public void delete(String className) throws DeleteResourceTypeException {
        if(checkCanDelete(className)) {
            this.resourceTypeRepository.delete(className);
        } else {
            throw new DeleteResourceTypeException("删除ResourceType错误：可能存在关联的ResourceRange");
        }
    }

    public void delete(String[] classNames) throws DeleteResourceTypeException {
        for(String className : classNames) {
            this.delete(className);
        }
    }

    private boolean checkCanDelete(String className) {
        ResourceType resourceType = this.resourceTypeRepository.findOne(className);
        if (resourceType == null) return false;
        return resourceType.getResourceRanges().size() == 0;
    }
}
