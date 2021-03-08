package pw.ewen.WLPT.domains.entities.acls;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * created by wenliang on 2021-03-08
 * 用于spring security acl的基础表
 */
@Entity
public class acl_class  implements Serializable {

    private static final long serialVersionUID = -1655443834331482632L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long id;

    @Column(name="class", nullable = false, length = 100, unique = true)
    private String classname;

    protected acl_class() {
    }

    public long getId() {
        return id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
