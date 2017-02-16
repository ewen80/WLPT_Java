package pw.ewen.WLPT.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import pw.ewen.WLPT.entity.Role;

/**
 * Created by wenliang on 17-2-16.
 * 用于实现动态查询
 */
public class RoleSpecifications {
    private RoleSpecifications(){}

    static Specification<Role> hasName(String name){
        return (root, query, cb) -> {
            return cb.like(root.get(Role_.name), name);
        };
    }
}
