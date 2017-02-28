package pw.ewen.WLPT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.ewen.WLPT.domain.entity.MyResourceRange;

/**
 * Created by wenliang on 17-2-27.
 */
public interface MyResourceRangeRepository extends ResourceRangeRepository<MyResourceRange>, JpaRepository<MyResourceRange, Long> {

}
