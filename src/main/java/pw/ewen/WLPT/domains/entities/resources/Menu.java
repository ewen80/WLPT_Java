package pw.ewen.WLPT.domains.entities.resources;

import pw.ewen.WLPT.domains.Resource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by wen on 17-5-7.
 * 菜单资源类
 */
@Entity
public class Menu extends Resource implements Serializable {

    private String name;
    private String path;
    private int orderId;
    private List<Menu> children;
    private Menu parent;

    public  Menu() {
        super();
    }

    /**
     * 菜单名
     * @return
     */
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 菜单路径
     * @return
     */
    @Column
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 排序号
     * @return
     */
    @Column
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * 子节点
     * @return
     */
    @OneToMany(mappedBy = "parent")
    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    /**
     * 父节点
     * @return
     */
    @ManyToOne
    @JoinColumn(name = "parent_Id")
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }
}
