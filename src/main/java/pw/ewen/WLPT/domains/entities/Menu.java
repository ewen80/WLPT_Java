package pw.ewen.WLPT.domains.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.domains.Resource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 17-5-7.
 * 菜单资源类
 */
@Entity
@Component
public class Menu extends Resource implements Serializable {

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
     * 菜单图表类
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

}
