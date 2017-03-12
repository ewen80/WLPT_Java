package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.domain.entity.ResourceRange;

import java.util.List;

/**
 * Created by wenliang on 17-2-28.
 * 资源范围仓储接口
 */
public interface ResourceRangeRepository extends JpaRepository<ResourceRange, Long>{
    List<ResourceRange> findByRoleIdAndResourceType(String roleId, String resourceType);
}
