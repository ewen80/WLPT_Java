package pw.ewen.WLPT.repositories.resources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pw.ewen.WLPT.domains.entities.resources.Menu;

import java.util.List;

/**
 * Created by wen on 17-5-7.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
    List<Menu> findByParent_id(long parentId);
    List<Menu> findByParentOrderByOrderId(Menu menu);
    List<Menu> findByOrderIdGreaterThanEqualAndParent_id(int orderId, Long parentId);
    //获取该父节点下orderId最大的menu
    Menu findTopByParent_idOrderByOrderIdDesc(Long parentId);
}
