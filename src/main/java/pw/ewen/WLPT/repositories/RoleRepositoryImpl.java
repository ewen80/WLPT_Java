package pw.ewen.WLPT.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色软删除
 * created by wenliang on 20210227
 */
public class RoleRepositoryImpl implements SoftDelete<String>{

    @PersistenceContext
    private EntityManager em;

    //软删除角色
    public int softdelete(List<String> ids) {
        String strIds = ids.stream().collect(Collectors.joining(","));
        //检查角色下是否有用户，如果有用户则不能删除
        String softDeleteSql =
                "UPDATE Role SET deleted=true WHERE id in (" + strIds + ")";
        return em.createQuery(softDeleteSql).executeUpdate();
    }
}
