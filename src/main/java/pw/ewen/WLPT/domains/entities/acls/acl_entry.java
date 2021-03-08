package pw.ewen.WLPT.domains.entities.acls;

import javax.persistence.*;
import java.io.Serializable;

/**
 * created by wenliang on 2021/3/8
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"acl_object_identity", "ace_order"}))
public class acl_entry implements Serializable {

    private static final long serialVersionUID = -5759797553813597359L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long id;

    @JoinColumn(nullable = false)
    private acl_object_identity acl_object_identity;

    @Column(nullable = false)
    private int ace_order;

    @JoinColumn(nullable = false)
    private acl_sid sid;

    @Column(nullable = false)
    private int mask;

    @Column(nullable = false)
    private boolean granting;

    @Column(nullable = false)
    private boolean audit_success;

    @Column(nullable = false)
    private boolean audit_failure;

    protected acl_entry() {
    }

    public long getId() {
        return id;
    }

    public pw.ewen.WLPT.domains.entities.acls.acl_object_identity getAcl_object_identity() {
        return acl_object_identity;
    }

    public void setAcl_object_identity(pw.ewen.WLPT.domains.entities.acls.acl_object_identity acl_object_identity) {
        this.acl_object_identity = acl_object_identity;
    }

    public int getAce_order() {
        return ace_order;
    }

    public void setAce_order(int ace_order) {
        this.ace_order = ace_order;
    }

    public acl_sid getSid() {
        return sid;
    }

    public void setSid(acl_sid sid) {
        this.sid = sid;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public boolean isGranting() {
        return granting;
    }

    public void setGranting(boolean granting) {
        this.granting = granting;
    }

    public boolean isAudit_success() {
        return audit_success;
    }

    public void setAudit_success(boolean audit_success) {
        this.audit_success = audit_success;
    }

    public boolean isAudit_failure() {
        return audit_failure;
    }

    public void setAudit_failure(boolean audit_failure) {
        this.audit_failure = audit_failure;
    }
}
