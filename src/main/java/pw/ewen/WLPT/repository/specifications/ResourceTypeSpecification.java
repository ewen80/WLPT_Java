package pw.ewen.WLPT.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import pw.ewen.WLPT.domain.entity.ResourceType;
import pw.ewen.WLPT.domain.entity.ResourceType_;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.Role_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wenliang on 17-3-22.
 * 用于对资源类型动态查询
 */
public class ResourceTypeSpecification implements Specification<ResourceType> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<ResourceType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (criteria.getOperation().equalsIgnoreCase("%")) {    //包含
            return cb.like(
                    root.<String> get(criteria.getKey()), "%"+criteria.getValue()+"%");
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {  //等于
            return cb.equal(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("!:")) {   //不等于
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return cb.notLike(
                        root.<String>get(criteria.getKey()), "%"+criteria.getValue()+"%");
            } else {
                return cb.notEqual(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        else if(criteria.getOperation().equalsIgnoreCase("~")){ //startswith
            return cb.substring(
                    root.<String>get(criteria.getKey()), );
        }
        else if(criteria.getOperation().equalsIgnoreCase("^")){ //endwith

        }
        return null;

    }
}
