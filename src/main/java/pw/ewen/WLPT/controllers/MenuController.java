package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.MenuDTO;
import pw.ewen.WLPT.domains.entities.Menu;
import pw.ewen.WLPT.repositories.MenuRepository;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.services.MenuService;

import java.util.List;

/**
 * Created by wen on 17-5-7.
 */
@RestController
@RequestMapping(value = "/resources/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserContext userContext;

//    /**
//     * 返回所有菜单树
//     * @return  （树形json格式）
//     */
//    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
//    public List<Menu> getTree() {
//        return this.menuService.getTree();
//    }

    /**
     * 返回有权限的菜单树
     * @return  树形json格式
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/authorized")
    public List<Menu> getAuthorizedMenuTree(){
        String userId = this.userContext.getCurrentUser().getUserId();
        return this.menuService.getPermissionMenuTree(userId);
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json")
    @PreAuthorize("hasAuthority(@propertyConfig.getDefaultAdminRoleId()) || hasPermission(#dto.convertToMenu(@menuRepository), 'write')")
    public MenuDTO save(@RequestBody MenuDTO dto){
        Menu menu = dto.convertToMenu(this.menuRepository);
        return MenuDTO.convertFromMenu(this.menuService.save(menu));
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/{menuId}")
    @PreAuthorize("hasAuthority(@propertyConfig.getDefaultAdminRoleId()) || hasPermission(@menuRepository.findOne(#menuId), 'write')")
    public void delete(@PathVariable("menuId") Long menuId) throws NumberFormatException{
        if(menuId != null){
            this.menuService.delete(menuId);
        }
    }
}
