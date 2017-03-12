package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.domain.entity.ResourceTypeList;

/**
 * Created by wen on 17-3-12.
 * 资源列表仓储
 */
public interface ResourceListRepository extends JpaRepository<ResourceTypeList, Long> {
}
