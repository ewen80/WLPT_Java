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

    /**
     * 获取顶级菜单
     * @return
     */
    public List<Menu> getTree() {
        return this.menuRepository.findByParentOrderByOrderId(null);
    }

    public Menu save(Menu  menu) {
        Menu parent = menu.getParent();
        Long parentId = parent == null ? null : parent.getId();
        if(menu.getOrderId() != 0){
            //如果新增的菜单的orderId不等于0,则将数据库中已经存在的orderId大于等于当前menu orderId的菜单项顺序id往后顺移一位
            List<Menu> afterCurMenus = this.menuRepository.findByOrderIdGreaterThanEqualAndParent_id(menu.getOrderId(), parentId);
            for(Menu m: afterCurMenus){
                m.setOrderId(m.getOrderId()+1);
            }

            this.menuRepository.save(afterCurMenus);
        } else {
            //如果当前menu orderId 等于0,则新增menu顺序为最后一位
            Menu lastOrderMenu = this.menuRepository.findTopByParent_idOrderByOrderIdDesc(parentId);
            if(lastOrderMenu != null){
                menu.setOrderId(lastOrderMenu.getOrderId()+1);
            }
        }

        return this.menuRepository.save(menu);
    }

    public void delete(long id) {
        this.menuRepository.delete(id);
    }
}
