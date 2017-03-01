package pw.ewen.WLPT.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.domain.entity.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wen on 17-2-18.
 */
public class UserSpecifications{
        private UserSpecifications(){}

        public static Specification<User> hasName(String name){
            return new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.like(root.get(User_.name), "%"+name+"%");
                }
            };
        }
}
