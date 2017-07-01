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

    private long resourceId;

    protected Resource() {
    }

    //资源唯一标志号
    @Id
    @GeneratedValue
    public long getResourceId(){
        return this.resourceId;
    }
    public void setResourceId(long resourceId){ this.resourceId = resourceId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        return resourceId == resource.resourceId;
    }

    @Override
    public int hashCode() {
        return (int) (resourceId ^ (resourceId >>> 32));
    }
}
