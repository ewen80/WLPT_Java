package pw.ewen.WLPT.domain.entity;

import pw.ewen.WLPT.domain.HasResourceRangeObject;
import pw.ewen.WLPT.domain.Resource;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

/*
 * 代表权限模块管理的资源
 *  
 */
@Entity
public  class MyResource extends Resource implements HasResourceRangeObject, Serializable {
    @Override
    @Transient
    public Class getResourceRangeObjectClass() {
        return MyResourceRange.class;
    }
}
