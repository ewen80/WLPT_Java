package pw.ewen.WLPT.services.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.resources.MenuRepository;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * 根据叶子节点菜单生成对应的菜单树
     * 思路：对每个叶子节点获取父节点，（此时可能需要从hibernate detach以便不要加载父节点的children属性）并将自己添加到父节点的children中
     * 重复这一过程，直到父节点为null.(递归函数)
     * @param leafMenus 叶子节点(已经是hibernate Persistent状态)
     * @return  包含叶子节点的完整树结构
     */
    public List<Menu> generateLeafMenusTree(List<Menu> leafMenus){
        List<Menu> results = new ArrayList<>();

        for(Menu menu: leafMenus){
            Menu parent = menu.getParent();

            if(parent != null){
                //如果menu已经存在于parent的children中则不再重复添加
                if(!parent.getChildren().contains(menu)){
                    parent.getChildren().add(menu);
                }
                List<Menu> parentMenu = this.generateLeafMenusTree(Collections.singletonList(parent));
                results = parentMenu;
            } else {
                results.add(menu);
            }
        }
        return results;
    }
}
