package pw.ewen.WLPT.domains.dtoconvertors.resources;

import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.services.resources.MenuService;

import java.util.List;

/**
 * created by wenliang on 2021/4/10
 */
public class MenuDTOConvertor {

    public Menu toMenu(MenuDTO menuDTO, MenuService menuService) {
        Menu menu = new Menu();
        menu.setId(menuDTO.getId());
        menu.setName(menuDTO.getName());
        menu.setOrderId(menuDTO.getOrderId());
        menu.setIconClass(menuDTO.getIconClass());
        menu.setPath(menuDTO.getPath());

        Menu parent = null;
        if(menuDTO.getParentId() != 0 ) {
            parent = menuService.findOne(menuDTO.getParentId());
            if(parent != null) {
                menu.setParent(parent);
            }
        }

//        List<Menu> children = menuService.findChildren(menuDTO.getId());
//        if(children != null) {
//            menu.setChildren(children);
//        }
        return menu;
    }

    public MenuDTO toDTO(Menu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setOrderId(menu.getOrderId());
        dto.setIconClass(menu.getIconClass());
        dto.setPath(menu.getPath());
        if(menu.getParent() != null){
            dto.setParentId(menu.getParent().getId());
        }

        List<Menu> childrenMenus = menu.getChildren();
        if(childrenMenus.size() > 0) {
            childrenMenus.forEach( (m) -> {
                MenuDTO d = this.toDTO(m);
                dto.getChildren().add(d);
            });
        }

        return dto;
    }
}
