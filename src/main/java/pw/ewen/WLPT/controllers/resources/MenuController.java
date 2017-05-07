package pw.ewen.WLPT.controllers.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.resources.MenuRepository;

import java.util.List;

/**
 * Created by wen on 17-5-7.
 */
@RestController
@RequestMapping(value = "/resources/menus")
public class MenuController {

    private MenuRepository menuRepository;

    @Autowired
    MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * 返回所有菜单信息
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Menu> getAll() {
        return this.menuRepository.findAll();
    }
}
