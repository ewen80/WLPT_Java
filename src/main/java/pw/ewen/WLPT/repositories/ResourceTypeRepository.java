package pw.ewen.WLPT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.domains.entities.ResourceType;

/**
 * Created by wen on 17-3-12.
 * 资源列型仓储
 */
public interface ResourceTypeRepository extends JpaRepository<ResourceType, String>, JpaSpecificationExecutor<ResourceType> {

}
