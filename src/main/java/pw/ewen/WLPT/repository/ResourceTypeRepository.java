package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.domain.entity.ResourceType;

/**
 * Created by wen on 17-3-12.
 * 资源列型仓储
 */
public interface ResourceTypeRepository extends JpaRepository<ResourceType, String>, JpaSpecificationExecutor<ResourceType> {

}
