package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.entity.Resource;
import pw.ewen.WLPT.entity.Role;

/**
 * Created by wen on 17-2-24.
 */
public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {
}
