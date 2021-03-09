package pw.ewen.WLPT.domains.entities.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pw.ewen.WLPT.domains.Resource;
import pw.ewen.WLPT.domains.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 17-5-7.
 * 菜单资源类
 */
@Entity
public class Menu extends Resource implements Serializable {

    private static final long serialVersionUID = 7179865199657578507L;

    private long id;
    private String name;
    private String path;
    private String iconClass = "";
    private int orderId = 0;
    private List<Menu> children;
    private Menu parent;

    public  Menu() {
        super();
        children = new ArrayList<>();
    }

    public Menu(String name){
        this();
        this.name = name;
    }

    public Menu(String name, String path){
        this(name);
        this.path = path;
    }

    public Menu(String name, String path, int orderId){
        this(name,path);
        this.orderId = orderId;
    }

    @Override
    @Id
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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
     * 菜单图标类
     * @return
     */
    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
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
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @OrderBy("orderId")
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
    @JsonIgnore
    public Menu getParent() {
        return parent;
    }
    @JsonProperty
    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Menu){
            if(((Menu)obj).getId() == this.id) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
