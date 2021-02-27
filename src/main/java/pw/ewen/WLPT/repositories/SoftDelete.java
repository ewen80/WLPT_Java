package pw.ewen.WLPT.repositories;

import java.io.Serializable;
import java.util.List;

/**
 * 软删除接口
 * created by wenliang on 20210226
 */
public interface SoftDelete<ID extends Serializable> {

    int softdelete(List<ID> ids);
}
