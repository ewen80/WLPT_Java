package pw.ewen.WLPT.services.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.resources.MenuRepository;

import java.util.List;

/**
 * Created by wenliang on 17-5-9.
 */
@Service
public class MenuService {

    private MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * 获取所有菜单
     * @return
     */
    public List<Menu> getAll() {
        return this.menuRepository.findAll(new Sort("orderId"));
    }

    public Menu save(Menu  menu) {
        return this.menuRepository.save(menu);
    }

    public void delete(Menu menu) {
        this.menuRepository.delete(menu);
    }

    public void delete(long id) {
        this.menuRepository.delete(id);
    }
}
