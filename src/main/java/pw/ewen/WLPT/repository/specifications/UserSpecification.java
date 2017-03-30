package pw.ewen.WLPT.repository.specifications;

import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.repository.specifications.core.SearchCriteria;
import pw.ewen.WLPT.repository.specifications.core.SearchSpecification;

/**
 * Created by wen on 17-3-29.
 */
public class UserSpecification extends SearchSpecification<User> {

    public UserSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
