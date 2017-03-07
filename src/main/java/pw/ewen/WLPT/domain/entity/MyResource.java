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

    private int number;

    public MyResource(int number) {
        this.number = number;
    }
    public MyResource() {
        super();
    }

    //测试用
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    @Transient
    public Class getResourceRangeObjectClass() {
        return MyResourceRange.class;
    }
}
