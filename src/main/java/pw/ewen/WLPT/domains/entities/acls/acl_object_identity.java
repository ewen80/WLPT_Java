package pw.ewen.WLPT.domains.entities.acls;

import javax.persistence.*;
import java.io.Serializable;

/**
 * created by wenliang on 2021/3/8
 * 用于spring security acl的基础表
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"object_id_class", "object_id_identity"}))
public class acl_object_identity implements Serializable {

    private static final long serialVersionUID = -1758492573763951578L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long id;

    @JoinColumn(nullable = false)
    private acl_class object_id_class;

    @Column(nullable = false)
    private long object_id_identity;

    @JoinColumn
    private acl_object_identity parent_object;

    @JoinColumn
    private acl_sid owner_sid;

    @Column(nullable = false)
    private boolean entries_inheriting;

    protected acl_object_identity() {
    }

    public long getId() {
        return id;
    }


    public acl_class getObject_id_class() {
        return object_id_class;
    }

    public void setObject_id_class(acl_class object_id_class) {
        this.object_id_class = object_id_class;
    }

    public long getObject_id_identity() {
        return object_id_identity;
    }

    public void setObject_id_identity(long object_id_identity) {
        this.object_id_identity = object_id_identity;
    }

    public acl_object_identity getParent_object() {
        return parent_object;
    }

    public void setParent_object(acl_object_identity parent_object) {
        this.parent_object = parent_object;
    }

    public acl_sid getOwner_sid() {
        return owner_sid;
    }

    public void setOwner_sid(acl_sid owner_sid) {
        this.owner_sid = owner_sid;
    }

    public boolean isEntries_inheriting() {
        return entries_inheriting;
    }

    public void setEntries_inheriting(boolean entries_inheriting) {
        this.entries_inheriting = entries_inheriting;
    }
}
