package pw.ewen.WLPT.controllers.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.resources.MenuDTO;
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

    private final MenuService menuService;

    @Autowired
    MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 返回顶级菜单
     * @return  （树形json格式）
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<MenuDTO> getTree() {
        return this.menuService.findTree().stream().map(MenuDTO::convertFromMenu).collect(Collectors.toList());
    }

    /**
     * 返回有权限的菜单树
     * @return  树形json格式
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{userId}")
    public List<MenuDTO> getAuthorizedMenuTree(@PathVariable("userId") String userId){
        return this.menuService.findPermissionMenuTree(userId).stream().map(MenuDTO::convertFromMenu).collect(Collectors.toList());
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json")
    public MenuDTO save(@RequestBody MenuDTO dto){
        Menu menu = dto.convertToMenu(this.menuService);
        return MenuDTO.convertFromMenu(this.menuService.save(menu));
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/{menuId}")
    public void delete(@PathVariable("menuId") String menuId) throws NumberFormatException{
        long longMenuId = Long.parseLong(menuId);
        this.menuService.delete(longMenuId);
    }
}
