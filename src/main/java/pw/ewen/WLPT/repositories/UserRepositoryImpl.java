package pw.ewen.WLPT.repositories;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于实现软删除功能
 */
public class UserRepositoryImpl implements SoftDelete<String>{

    @PersistenceContext
    private EntityManager em;

    public int softdelete(List<String> ids) {
        String strIds = ids.stream().collect(Collectors.joining(","));
        String softDeleteSql =
                "UPDATE User SET deleted=true WHERE id in (" + strIds + ")";
        return em.createQuery(softDeleteSql).executeUpdate();
    }
}
