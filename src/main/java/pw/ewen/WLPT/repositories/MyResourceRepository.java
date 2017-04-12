package pw.ewen.WLPT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pw.ewen.WLPT.domains.entities.MyResource;

/**
 * Created by wen on 17-2-24.
 */
public interface MyResourceRepository extends JpaRepository<MyResource, Long>, JpaSpecificationExecutor<MyResource> {
}
