package pw.ewen.WLPT.domain.entity;

import pw.ewen.WLPT.domain.HasResourceRangeObject;
import pw.ewen.WLPT.domain.Resource;
import pw.ewen.WLPT.domain.entity.MyResourceRange;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/*
 * 代表权限模块管理的资源
 *  
 */
@Entity
public  class MyResource extends Resource implements Serializable, HasResourceRangeObject {
	//得到RangeClass
	//不要序列化
	@Transient
	@Override
	public Class getResourceRangeObjectClass() {
		return MyResourceRange.class;
	}
}