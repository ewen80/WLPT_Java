package pw.ewen.WLPT.entity;

import javax.persistence.Entity;

/**
 * Created by wenliang on 17-2-27.
 */
@Entity
public class MyResourceRange extends  ResourceRange {
    public MyResourceRange(long id, String filter) {
        super(id, filter);
    }

    @Override
    public ResourceRange getOne(Object domainObject) {
        return null;
    }
}
