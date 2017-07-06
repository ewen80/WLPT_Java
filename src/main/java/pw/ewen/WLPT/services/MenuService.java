package pw.ewen.WLPT.services;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.Menu;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.MenuRepository;
import pw.ewen.WLPT.security.acl.ObjectIdentityRetrievalStrategyWLPTImpl;

import javax.persistence.EntityManager;
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
    private UserService userService;
    @Autowired
    private EntityManager entityManager;
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
     * 获取所有顶级菜单
     * @return
     */
    public List<Menu> getTree() {
        return this.menuRepository.findByParentOrderByOrderId(null);
    }

    /**
     * 获取角色有权限的菜单节点
     * 思路：1.获取所有菜单节点（非树形）
     *      2.获取所有有权限的菜单节点
     *      3.根据2产生的节点，生成叶子节点
     *      4.根据叶子节点生成对应树形
     * @param role 角色
     * @return
     */
    public List<Menu> getPermissionMenuTree(Role role){
        List<Menu> allMenus = this.getAll();
        List<Menu> myMenus = this.authorizedmenus(allMenus, role, Arrays.asList(BasePermission.READ, BasePermission.WRITE));
        List<Menu> authorizedLeafMenus = this.generatePermissionLeafMenus(myMenus, role);
        List<Menu> permissionMenuTree = this.generateUpflowTree(authorizedLeafMenus);
        return permissionMenuTree;
    }
    /**
     * 获取用户有权限的菜单节点
     * @param user  用户
     * @return
     */
    public List<Menu> getPermissionMenuTree(User user){
        return this.getPermissionMenuTree(user.getRole());
    }
    /**
     * 获取用户有权限的菜单节点
     * @param userId  用户id
     * @return  userId对应的有权限的菜单树，如果userId没有对应的用户则返回null
     */
    public List<Menu> getPermissionMenuTree(String userId){
        User user = this.userService.findOne(userId);
        if(user != null){
            return this.getPermissionMenuTree(user);
        } else {
            return null;
        }
    }

    public Menu save(Menu  menu) {
        Menu parent = menu.getParent();
        Long parentId = parent == null ? null : parent.getResourceId();
        if(menu.getOrderId() != 0){
            //如果新增的菜单的orderId不等于0,则将数据库中已经存在的orderId大于等于当前menu orderId的菜单项顺序id往后顺移一位
            List<Menu> afterCurMenus = this.menuRepository.findByOrderIdGreaterThanEqualAndParent_resourceId(menu.getOrderId(), parentId);
            for(Menu m: afterCurMenus){
                m.setOrderId(m.getOrderId()+1);
            }

            this.menuRepository.save(afterCurMenus);
        } else {
            //如果当前menu orderId 等于0,则新增menu顺序为最后一位
            Menu lastOrderMenu = this.menuRepository.findTopByParent_resourceIdOrderByOrderIdDesc(parentId);
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
     * 思路：对每个叶子节点获取父节点，清空父节点的children属性，并将自己添加到父节点的children中
     * 重复这一过程，直到父节点为null.(递归函数)
     * 如果子节点非叶子节点，则生成的tree中该节点所有子节点都被包含
     * @param childMenus 叶子节点s
     * @return  包含叶子节点的完整树结构
     */
    private List<Menu> generateUpflowTree(List<Menu> childMenus){
        List<Menu> results = new ArrayList<>();

        for(Menu menu: childMenus){
            Menu parent = menu.getParent();

            if(parent != null){
                //把parent状态设置为detach,使得对children的更改不会同步到数据库中
                this.entityManager.detach(parent);

                //如果menu已经存在于parent的children中则不再重复添加
                try {
                    if(!parent.getChildren().contains(menu)) {
                        parent.getChildren().add(menu);
                    }
                } catch (HibernateException e){ //捕获hibernate的懒加载报错，如果报错说明还没有set过children集合
                    parent.setChildren(new ArrayList<Menu>());
                    parent.getChildren().add(menu);
                }

                List<Menu> parentMenus = this.generateUpflowTree(Collections.singletonList(parent));
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
    private List<Menu> generatePermissionLeafMenus(List<Menu> menus, Role role){
        List<Menu> childrenMenus = new ArrayList<>();

        for(Menu menu: menus){
            List<Menu> children = menu.getChildren();
            //判断是否是叶子节点
            if(children.size() > 0){
                List<Menu> menusIsAuthorized = this.authorizedmenus(children, role, Arrays.asList(BasePermission.READ, BasePermission.WRITE));

                if(menusIsAuthorized.size() == 0){
                    //子节点中没有被授权的节点
                    menusIsAuthorized = children;
                }

                for(Menu child: menusIsAuthorized){
                    List<Menu> leafMenus = this.generatePermissionLeafMenus(Collections.singletonList(child), role);
                    for(Menu m: leafMenus){
                        if(!childrenMenus.contains(m)){
                            childrenMenus.add(m);
                        }
                    }
                }
            } else {
                //已经是叶子节点
                if(!childrenMenus.contains(menu)){
                    childrenMenus.add(menu);
                }
            }
        }
        return childrenMenus;
    }

    /**
     * 菜单(s)是否已经被授权
     * @param menus
     * @return  被授权的菜单列表
     */
    private List<Menu> authorizedmenus(List<Menu> menus, Role myRole, List<Permission> permissions) {
        List<Menu> authorizedMenus = new ArrayList<>();
        for(Menu menu: menus){
            ObjectIdentity menuOI = objectIdentityRetrieval.getObjectIdentity(menu);
            GrantedAuthoritySid sid = new GrantedAuthoritySid(myRole.getId());
            try{
                Acl acl = aclService.readAclById(menuOI, Collections.singletonList(sid));
                if(acl.isGranted(permissions, Collections.singletonList(sid), true)){
                    authorizedMenus.add(menu);
                }
            } catch(NotFoundException e){
                //没有找到acl，菜单对应的ResourceRange没有找到（没有对该菜单做过任何权限设置）

            }

        }
        return authorizedMenus;
    }

}
