package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.entity.ResourceRange;

/**
 * Created by wenliang on 17-2-27.
 */
public interface MyResourceRangeRepository extends JpaRepository<ResourceRange, Long> {
}
