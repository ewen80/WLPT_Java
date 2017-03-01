package pw.ewen.WLPT.domain;

import java.io.Serializable;

/**
 * Created by wen on 17-2-28.
 * 有对应的仓储对象
 */
public interface HasResourceRangeRepository<T, ID extends Serializable> {
    Class getRepositroyClass();
}
