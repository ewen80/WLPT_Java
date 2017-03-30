package pw.ewen.WLPT.repository.specifications;

import pw.ewen.WLPT.domain.entity.MyResource;
import pw.ewen.WLPT.repository.specifications.core.SearchCriteria;
import pw.ewen.WLPT.repository.specifications.core.SearchSpecification;

/**
 * Created by wen on 17-3-29.
 */
public class MyResourceSpecification extends SearchSpecification<MyResource> {
    public MyResourceSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
