package pw.ewen.WLPT.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/*
 * 代表权限模块管理的资源
 *  
 */
@Entity
public  class Resource implements Serializable, HasRangeObject {
	private long id;
	private int number;

	//资源唯一标志号
	@Id
	public long getId(){
		return this.id;
	}
	public void setId(long id){ this.id = id;}

	//得到RangeClass
	@Override
	public Class getRangeObjectClass() {
		return MyResourceRange.class;
	}

	//测试用
	public int getNumber(){ return this.number;}
	public void setNumber(int number){ this.number = number;}
}
