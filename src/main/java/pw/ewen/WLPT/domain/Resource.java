package pw.ewen.WLPT.domain;

import pw.ewen.WLPT.domain.entity.MyResourceRange;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Created by wen on 17-2-28.
 * 资源抽象类
 */
@MappedSuperclass
public abstract class Resource {
    private long id;
    private int number;

    //资源唯一标志号
    @Id
    public long getId(){
        return this.id;
    }
    public void setId(long id){ this.id = id;}



    //测试用
    public int getNumber(){ return this.number;}
    public void setNumber(int number){ this.number = number;}
}
