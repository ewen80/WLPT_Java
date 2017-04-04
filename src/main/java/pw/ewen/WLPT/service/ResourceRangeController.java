package pw.ewen.WLPT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domain.entity.ResourceRange;
import pw.ewen.WLPT.repository.ResourceRangeRepository;
import pw.ewen.WLPT.repository.specifications.ResourceRangeSpecificationBuilder;

import java.util.List;

/**
 * Created by wen on 17-4-3.
 */
@RestController
@RequestMapping(value = "/resourceranges")
public class ResourceRangeController {

    private ResourceRangeRepository resourceRangeRepository;

    @Autowired
    public ResourceRangeController(ResourceRangeRepository resourceRangeRepository) {
        this.resourceRangeRepository = resourceRangeRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<ResourceRange> getByResourceType(@RequestParam(value = "resourcetype", defaultValue = "") String resourceTypeClassName){
        if(resourceTypeClassName.isEmpty()){
            return resourceRangeRepository.findAll();
        }else{
            ResourceRangeSpecificationBuilder builder = new ResourceRangeSpecificationBuilder();
            return resourceRangeRepository.findAll(builder.build("resourceType:"+resourceTypeClassName));
        }

    }
}
