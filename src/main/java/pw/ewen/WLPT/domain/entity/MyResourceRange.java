package pw.ewen.WLPT.domain.entity;

import pw.ewen.WLPT.domain.ResourceRange;
import pw.ewen.WLPT.repository.MyResourceRangeRepository;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by wenliang on 17-2-27.
 * 测试资源类
 */
@Entity
public class MyResourceRange extends ResourceRange implements Serializable {

    public MyResourceRange(){ super();}
    public MyResourceRange(long id, String filter, String userId) {
        super(id, filter, userId);
    }

    @Override
    @Transient
    public Class<MyResourceRangeRepository> repositoryClass() {
        return MyResourceRangeRepository.class;
    }

    @Override
    public MyResourceRange generate_No_User_Matched_ResourceRange() {
        return new MyResourceRange(0, "no filter", "no user");
    }
}
