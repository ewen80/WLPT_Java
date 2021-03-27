package pw.ewen.WLPT.repositories;


import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于实现软删除功能
 * created by wenliang on 20210227
 */
public class UserRepositoryImpl implements SoftDelete<String>{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public int softdelete(List<String> ids) {
        StringBuilder sb = new StringBuilder();
        ids.forEach((id) -> {
            sb.append("'").append(id).append("',");
        });

        String strIds = sb.deleteCharAt(sb.length() - 1).toString();
        String softDeleteSql =
                "UPDATE User SET deleted=true WHERE id in (" + strIds + ")";
        return em.createQuery(softDeleteSql).executeUpdate();
    }
}
