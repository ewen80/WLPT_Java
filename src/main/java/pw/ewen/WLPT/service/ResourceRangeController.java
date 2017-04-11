package pw.ewen.WLPT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domain.DTO.ResourceRange.ResourceRangeDTO;
import pw.ewen.WLPT.domain.entity.ResourceRange;
import pw.ewen.WLPT.repository.ResourceRangeRepository;
import pw.ewen.WLPT.repository.ResourceTypeRepository;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.repository.specifications.ResourceRangeSpecificationBuilder;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wen on 17-4-3.
 */
@RestController
@RequestMapping(value = "/resourceranges")
public class ResourceRangeController {

    private ResourceRangeRepository resourceRangeRepository;
    private RoleRepository roleRepository;
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    public ResourceRangeController(ResourceRangeRepository resourceRangeRepository, RoleRepository roleRepository, ResourceTypeRepository resourceTypeRepository) {
        this.resourceRangeRepository = resourceRangeRepository;
        this.resourceTypeRepository = resourceTypeRepository;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<ResourceRange> getByResourceType(@RequestParam(value = "resourceclassname", defaultValue = "") String resourceTypeClassName){
        if(resourceTypeClassName.isEmpty()){
            return this.resourceRangeRepository.findAll();
        }else{
            ResourceRangeSpecificationBuilder builder = new ResourceRangeSpecificationBuilder();
            return this.resourceRangeRepository.findAll(builder.build("resourceType.className:"+resourceTypeClassName));
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResourceRangeDTO save(@RequestBody ResourceRangeDTO dto){
        ResourceRange range =  this.resourceRangeRepository.save(dto.convertToResourceRange(this.roleRepository, this.resourceTypeRepository));
        ResourceRangeDTO rangeDTO =  dto.convertFromResourceRange(range);
        return rangeDTO;
    }

    /**
     * TODO:下一个版本中加入该功能
     * ResourceRange是否重叠（相同Type和Role不能重复）
     * @param dto
     * @return
     */
    @RequestMapping(value = "/validators/resourcerangeOverlapping")
    public Boolean checkResourceRangeOverlapping(ResourceRangeDTO dto){
        List<ResourceRange> ranges = this.resourceRangeRepository.findByRole_idAndResourceType_className(dto.getRoleId(),dto.getResourceTypeClassName());
        return null;
    }
}
