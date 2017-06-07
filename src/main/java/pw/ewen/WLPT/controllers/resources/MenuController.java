package pw.ewen.WLPT.controllers.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.resources.MenuRepository;
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
    private MenuRepository menuRepository;

    @Autowired
    MenuController(MenuService menuService, MenuRepository menuRepository) {
        this.menuService = menuService;
        this.menuRepository = menuRepository;
    }

    /**
     * 返回菜单信息
     * @return  （树形json格式）
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Menu> getTree() {
        return this.menuService.getTree();
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json")
    public MenuDTO save(@RequestBody MenuDTO dto){
        Menu menu = dto.convertToMenu(this.menuRepository);
        return MenuDTO.convertFromMenu(this.menuService.save(menu));
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/{menuId}")
    public void delete(@PathVariable("menuId") String menuId) throws NumberFormatException{
        Long longMenuId = Long.valueOf(menuId);
        this.menuService.delete(longMenuId);
    }
}
