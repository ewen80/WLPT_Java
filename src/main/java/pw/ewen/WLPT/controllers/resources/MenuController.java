package pw.ewen.WLPT.controllers.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.services.resources.MenuService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wen on 17-5-7.
 */
@RestController
@RequestMapping(value = "/resources/menus")
public class MenuController {

    private MenuService menuService;

    @Autowired
    MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 返回所有菜单信息
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
//    public List<MenuDTO> getAll() {
//        List<Menu> menus = this.menuService.getAll();
//        return menus.stream()
//                .map( menu -> MenuDTO.convertFromMenu(menu))
//                .collect(Collectors.toList());
//    }

    public List<Menu> getAll() {
        return this.menuService.getAll();
    }
}
