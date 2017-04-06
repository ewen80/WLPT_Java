package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.domain.entity.ResourceRange;
import pw.ewen.WLPT.domain.entity.Role;

import java.util.List;

/**
 * Created by wenliang on 17-2-28.
 * 资源范围仓储接口
 */
public interface ResourceRangeRepository extends JpaRepository<ResourceRange, Long>, JpaSpecificationExecutor<ResourceRange>{
    /**
     * 根据角色和资源找出资源范围
     * @param role
     * @param resourceTypeClassName
     * @return
     */
    List<ResourceRange> findByRoleAndResourceType_className(Role role, String resourceTypeClassName);
}
