package pw.ewen.WLPT.domain.entity;

import pw.ewen.WLPT.domain.Resource;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/*
 * 代表权限模块管理的资源
 *  
 */
@Entity
public  class MyResource extends Resource implements Serializable {

//    private ResourceType resourceType;

    private int number;

    public MyResource(int number) {
        this.number = number;
    }
    public MyResource() {
        super();
    }

//    @ManyToOne
//    @JoinColumn(name = "resourceType_Id")
//    public ResourceType getResourceType() {
//        return resourceType;
//    }
//    public void setResourceType(ResourceType resourceType) {
//        this.resourceType = resourceType;
//    }

    //测试用
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
