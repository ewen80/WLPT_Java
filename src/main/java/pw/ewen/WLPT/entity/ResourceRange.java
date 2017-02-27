package pw.ewen.WLPT.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by wen on 17-2-26.
 * 资源范围类
 * 代表某个范围的资源集合
 */
public abstract class ResourceRange implements Serializable {
    private long id;
    private String filter;
    private String sid;

    protected ResourceRange(){}
    public ResourceRange(long id, String filter) {
        this.id = id;
        this.filter = filter;
    }

    @Id
    @GeneratedValue
    public long getId(){ return this.id;}
    public void setId(long value){ this.id = value;}

    //资源范围筛选依据（Spel）
    public String getFilter() {
        return filter;
    }
    public void setFilter(String filter) {
        this.filter = filter;
    }

    //角色ID
    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
}
