package pw.ewen.WLPT.domains.DTOs.resources;

import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.dtoconvertors.resources.MenuDTOConvertor;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.resources.MenuRepository;
import pw.ewen.WLPT.services.resources.MenuService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 17-5-7.
 */
public class MenuDTO {

    private long id;
    private String name;
    private String path;
    private int orderId;
    private String iconClass;
    private List<MenuDTO> children = new ArrayList<>();
    private long parentId; //默认0,表示无上级节点

    /**
     * 转化为Menu对象
     */
    public Menu convertToMenu(MenuService menuService) {
        MenuDTOConvertor converter = new MenuDTOConvertor();
        return converter.toMenu(this, menuService);
    }

    /**
     * 转换为MenuDTO对象
     */
    public static MenuDTO convertFromMenu(Menu menu) {
        MenuDTOConvertor converter = new MenuDTOConvertor();
        return converter.toDTO(menu);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }
}
