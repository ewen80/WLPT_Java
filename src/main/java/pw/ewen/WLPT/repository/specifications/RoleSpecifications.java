package pw.ewen.WLPT.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.Role_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wenliang on 17-2-16.
 * 用于实现动态查询
 */
public class RoleSpecifications {
    private RoleSpecifications(){}

    public static Specification<Role> hasName(String name){
        return new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get(Role_.name), "%"+name+"%");
            }
        };
    }
}

