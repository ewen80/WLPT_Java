package pw.ewen.WLPT.domains.entities.acls;

import javax.persistence.*;

/**
 * created by wenliang on 2021-03-08
 * 用于spring security acl的基础表
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"sid", "principal"}))
public class acl_sid {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long identity;

    @Column(nullable = false)
    private boolean principal;

    @Column(nullable = false, length = 100)
    private String sid;
}
