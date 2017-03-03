package pw.ewen.WLPT.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by wen on 17-2-28.
 * 资源抽象类
 */
@MappedSuperclass
public abstract class Resource {
    private long id;
    private int number;

    protected Resource(long id, int number) {
        this.id = id;
        this.number = number;
    }

    protected Resource() {
    }

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
