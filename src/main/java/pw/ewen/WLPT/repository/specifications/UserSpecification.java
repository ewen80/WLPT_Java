package pw.ewen.WLPT.repository.specifications;

import pw.ewen.WLPT.domain.entity.User;

/**
 * Created by wen on 17-3-29.
 */
public class UserSpecification extends SearchSpecification<User> {

    public UserSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
