package pw.ewen.WLPT.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pw.ewen.WLPT.domain.entity.ResourceType;

import java.util.List;

/**
 * Created by wen on 17-3-12.
 * 资源列型仓储
 */
public interface ResourceTypeRepository extends JpaRepository<ResourceType, String> {
    @Query("select rt from ResourceType rt where deleted = false")
    Page<ResourceType> findAllValidResources(Pageable pageable);

}
