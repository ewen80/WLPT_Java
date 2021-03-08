package pw.ewen.WLPT.domains.entities.acls;

import javax.persistence.*;
import java.io.Serializable;

/**
 * created by wenliang on 2021-03-08
 * 用于spring security acl的基础表
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"sid", "principal"}))
public class acl_sid implements Serializable {

    private static final long serialVersionUID = -5823503649328681538L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private boolean principal;

    @Column(nullable = false, length = 100)
    private String sid;

    protected acl_sid() {
    }

    public long getId() {
        return id;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
