package pw.ewen.WLPT.repository;

import pw.ewen.WLPT.domain.entity.ResourceRange;

import java.util.List;

/**
 * Created by wenliang on 17-2-28.
 * 资源范围仓储接口
 */
public interface ResourceRangeRepository<T extends ResourceRange>{
    List<T> findByRoleId(String roleId);
}
