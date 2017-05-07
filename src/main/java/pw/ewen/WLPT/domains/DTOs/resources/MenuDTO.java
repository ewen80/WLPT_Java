package pw.ewen.WLPT.domains.DTOs.resources;

import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.DTOs.DTOConvert;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.resources.MenuRepository;

import java.util.List;

/**
 * Created by wen on 17-5-7.
 */
public class MenuDTO {

    private long id;
    private String name;
    private String path;
    private int orderId;
    private long parentId;

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
            menu.setId(menuDTO.getId());
            menu.setName(menuDTO.getName());
            menu.setOrderId(menuDTO.getOrderId());
            menu.setPath(menuDTO.getPath());

            Menu parent = null;
            if(menuDTO.getParentId() != 0 ) {
                parent = this.menuRepository.findOne(menuDTO.getParentId());
                if(parent != null) {
                    menu.setParent(parent);
                }
            }

            List<Menu> children = this.menuRepository.findByparent_id(menuDTO.getId());
            if(children != null) {
                menu.setChildren(children);
            }
            return menu;
        }

        @Override
        public MenuDTO doBackward(Menu menu) {
            return null;
        }
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

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
