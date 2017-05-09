package pw.ewen.WLPT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.ResourceRange;

import java.util.List;

/**
 * Created by wenliang on 17-2-28.
 * 资源范围仓储接口
 */
@Repository
public interface ResourceRangeRepository extends JpaRepository<ResourceRange, Long>, JpaSpecificationExecutor<ResourceRange>{
    /**
     * 根据角色和资源找出资源范围
     * @param roleId
     * @param resourceTypeClassName
     * @return
     */
    List<ResourceRange> findByRole_idAndResourceType_className(String roleId, String resourceTypeClassName);
}
