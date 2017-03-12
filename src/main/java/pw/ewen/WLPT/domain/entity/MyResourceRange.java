package pw.ewen.WLPT.domain.entity;

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
    public MyResourceRange(String filter, String roleId) {
        super(filter, roleId);
    }

    @Override
    @Transient
    public Class<MyResourceRangeRepository> repositoryClass() {
        return MyResourceRangeRepository.class;
    }

//    @Override
//    public MyResourceRange generate_No_Role_Matched_ResourceRange() {
//        return new MyResourceRange(-1, "no filter", "no role");
//    }
}
