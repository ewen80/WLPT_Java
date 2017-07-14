package pw.ewen.WLPT.domains.DTOs;

import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.entities.Menu;
import pw.ewen.WLPT.repositories.MenuRepository;

import java.util.List;

/**
 * Created by wen on 17-5-7.
 */
public class MenuDTO {

    private long resourceId;
    private String name;
    private String path;
    private int orderId;
    private String iconClass;
    private long parentId; //默认0,表示无上级节点

    private static class MenuConverter implements DTOConvert<MenuDTO, Menu>  {

        private MenuRepository menuRepository;

        MenuConverter(){}
        MenuConverter(MenuRepository menuRepository) {
            this.menuRepository = menuRepository;
        }

        @Override
        public Menu doForward(MenuDTO menuDTO) {
            Assert.notNull(this.menuRepository);

            Menu menu = new Menu();
            menu.setId(menuDTO.getResourceId());
            menu.setName(menuDTO.getName());
            menu.setOrderId(menuDTO.getOrderId());
            menu.setIconClass(menuDTO.getIconClass());
            menu.setPath(menuDTO.getPath());

            Menu parent = null;
            if(menuDTO.getParentId() != 0 ) {
                parent = this.menuRepository.findOne(menuDTO.getParentId());
                if(parent != null) {
                    menu.setParent(parent);
                }
            }

            List<Menu> children = this.menuRepository.findByParent_id(menuDTO.getResourceId());
            if(children != null) {
                menu.setChildren(children);
            }
            return menu;
        }

        @Override
        public MenuDTO doBackward(Menu menu) {
            MenuDTO dto = new MenuDTO();
            dto.setResourceId(menu.getId());
            dto.setName(menu.getName());
            dto.setOrderId(menu.getOrderId());
            dto.setIconClass(menu.getIconClass());
            dto.setPath(menu.getPath());
            if(menu.getParent() != null){
                dto.setParentId(menu.getParent().getId());
            }

            return dto;
        }
    }

    /**
     * 转化为Menu对象
     * @return
     */
    public Menu convertToMenu(MenuRepository menuRepository) {
        MenuConverter converter = new MenuConverter(menuRepository);
        return converter.doForward(this);
    }

    /**
     * 转换为MenuDTO对象
     * @param menu
     * @return
     */
    public static MenuDTO convertFromMenu(Menu menu) {
        MenuConverter converter = new MenuConverter();
        return converter.doBackward(menu);
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
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
}
