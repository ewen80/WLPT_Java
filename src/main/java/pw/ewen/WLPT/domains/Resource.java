package pw.ewen.WLPT.domains;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by wen on 17-2-28.
 * 资源抽象类
 */
@MappedSuperclass
public abstract class Resource {

    private long id;

    protected Resource() {
    }

    //资源唯一标志号
    @Id
    @GeneratedValue
    public long getId(){
        return this.id;
    }
    public void setId(long id){ this.id = id;}


}
