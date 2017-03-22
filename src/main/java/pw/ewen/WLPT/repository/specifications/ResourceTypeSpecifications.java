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
public class ResourceTypeSpecifications {

    //显示全部已删除项
    public static Specification<ResourceType> allDeleted(){
        return new Specification<ResourceType>(){
            public Predicate toPredicate(Root<ResourceType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get(ResourceType_.deleted), true);
            }
        };
    }

    //显示全部有效项
    public static Specification<ResourceType> allAvailable(){
        return (root, query, cb) -> cb.equal(root.get(ResourceType_.deleted),false);
    }
}
