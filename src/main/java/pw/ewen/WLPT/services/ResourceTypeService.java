package pw.ewen.WLPT.services;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
=======
>>>>>>> 3061c9d2cd27923bb33af2f8fb1dff0aa997da04
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

/**
<<<<<<< HEAD
 * created by wenliang on 20210226
=======
 * created by wenliang on 2021-2-24
>>>>>>> 3061c9d2cd27923bb33af2f8fb1dff0aa997da04
 */
@Service
public class ResourceTypeService {

    private ResourceTypeRepository resourceTypeRepository;

<<<<<<< HEAD
    @Autowired
=======
>>>>>>> 3061c9d2cd27923bb33af2f8fb1dff0aa997da04
    public ResourceTypeService(ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
    }

    public ResourceType save(ResourceType resourceType) {
        return this.resourceTypeRepository.save(resourceType);
    }
}
