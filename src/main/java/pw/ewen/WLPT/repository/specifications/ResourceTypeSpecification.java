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
        switch (criteria.getOperation()){
            case
        }
    }
}
