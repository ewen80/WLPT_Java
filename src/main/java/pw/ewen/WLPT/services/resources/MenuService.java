package pw.ewen.WLPT.services.resources;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.resources.MenuRepository;
import pw.ewen.WLPT.security.UserContext;
import pw.ewen.WLPT.security.acl.ObjectIdentityRetrievalStrategyWLPTImpl;
import pw.ewen.WLPT.services.PermissionService;
import pw.ewen.WLPT.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wenliang on 17-5-9.
 */
@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MutableAclService aclService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private ObjectIdentityRetrievalStrategyWLPTImpl objectIdentityRetrieval;


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
     * 根据子节点菜单生成对应的菜单树
     * 思路：对每个叶子节点获取父节点，（此时可能需要从hibernate detach以便不要加载父节点的children属性）并将自己添加到父节点的children中
     * 重复这一过程，直到父节点为null.(递归函数)
     * @param menuIds 叶子节点ids
     * @return  包含叶子节点的完整树结构
     */
    public List<Menu> generateUpflowTree(Iterable<Long> menuIds){
        List<Menu> childMenus = this.menuRepository.findAll(menuIds);
        List<Menu> results = new ArrayList<>();

        for(Menu menu: childMenus){
            Menu parent = menu.getParent();

            if(parent != null){
                //如果menu已经存在于parent的children中则不再重复添加
                if(!parent.getChildren().contains(menu)){
                    parent.getChildren().add(menu);
                }
                List<Menu> parentMenus = this.generateUpflowTree(Collections.singletonList(parent.getId()));
//                results = parentMenu;
                for(Menu parentMenu: parentMenus){
                    if(!results.contains(parentMenu)){
                        results.add(parentMenu);
                    }
                }
            } else {
                results.add(menu);
            }
        }
        return results;
    }

    /**
     * 根据给定的菜单节点生成有权限的叶子节点
     * 思路：检查节点的所有子节点，是否存在于权限系统中，如果没有，则所有子节点均有效，如果有，则只有有权限的子节点有效，其他节点删除，接着往下递归，直到叶子节点。
     * @param menus 子节点
     * @return  有权限的叶子节点
     */
    public List<Menu> generatePermissionLeafMenus(List<Menu> menus){
        List<Menu> childrenMenus = new ArrayList<>();

        for(Menu menu: menus){
            List<Menu> children = menu.getChildren();
            //判断是否是叶子节点
            if(children.size() > 0){
                List<Menu> menusIsAuthorized = this.menusIsAuthorized(children, this.userContext.getCurrentUser().getRole(), Arrays.asList(BasePermission.READ, BasePermission.WRITE));

                if(menusIsAuthorized.size() == 0){
                    //子节点中没有被授权的节点
                    menusIsAuthorized = children;
                }

                for(Menu child: menusIsAuthorized){
                    childrenMenus.addAll(this.generatePermissionLeafMenus(Collections.singletonList(child)));
                }
            } else {
                //已经是叶子节点
                childrenMenus.add(menu);
            }
        }
        return childrenMenus;
    }

    /**
     * 菜单(s)是否已经被授权,列表中有一个被授权则返回true
     * @param menus
     * @return  被授权的菜单列表
     */
    private List<Menu> menusIsAuthorized(List<Menu> menus, Role myRole, List<Permission> permissions) {
        List<Menu> authorizedMenus = new ArrayList<>();
        for(Menu menu: menus){
            ObjectIdentity menuOI = objectIdentityRetrieval.getObjectIdentity(menu);
            GrantedAuthoritySid sid = new GrantedAuthoritySid(myRole.getId());
            Acl acl = aclService.readAclById(menuOI, Collections.singletonList(sid));
            if(acl.isGranted(permissions, Collections.singletonList(sid), true)){
                authorizedMenus.add(menu);
            }
        }
        return authorizedMenus;
    }

}
