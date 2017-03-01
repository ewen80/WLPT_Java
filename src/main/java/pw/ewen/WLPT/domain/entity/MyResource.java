package pw.ewen.WLPT.domain.entity;

import pw.ewen.WLPT.domain.Resource;

import javax.persistence.Entity;
import java.io.Serializable;

/*
 * 代表权限模块管理的资源
 *  
 */
@Entity
public  class MyResource extends Resource implements Serializable {
	//得到RangeClass
	//不要序列化
//	@Transient
//	@Override
//	public Class getResourceRangeObjectClass() {
//		return MyResourceRange.class;
//	}
}
