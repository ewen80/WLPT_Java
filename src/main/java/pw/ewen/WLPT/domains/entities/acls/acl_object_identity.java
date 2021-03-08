package pw.ewen.WLPT.domains.entities.acls;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * created by wenliang on 2021/3/8
 * 用于spring security acl的基础表
 */
public class acl_object_identity implements Serializable {
    private static final long serialVersionUID = -1758492573763951578L;

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long id;

    private long object_id_class;

    private long object_id_identity;

    private long parent_object;

    private long owner_sid;

    private boolean entries_inheriting;
}
