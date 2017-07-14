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

//    protected Resource() {
//    }

    //资源唯一标志号
    //由于spring security acl要求，属性名只能用id
    @Id
    @GeneratedValue
    public long getId(){
        return this.id;
    }
    public void setId(long id){ this.id = id;}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return id == resource.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
