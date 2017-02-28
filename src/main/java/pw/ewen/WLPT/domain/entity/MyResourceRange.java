package pw.ewen.WLPT.domain.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import pw.ewen.WLPT.domain.HasResourceRangeRepository;
import pw.ewen.WLPT.domain.ResourceRange;
import pw.ewen.WLPT.repository.MyResourceRangeRepository;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by wenliang on 17-2-27.
 * 测试资源类
 */
@Entity
public class MyResourceRange extends ResourceRange implements Serializable {

    public MyResourceRange(long id, String filter, String userId, MyResourceRangeRepository myResourceRangeRepository) {
        super(id, filter, userId, myResourceRangeRepository);
    }

    @Override
    public Class<MyResourceRangeRepository> getRepositoryClass() {
        return MyResourceRangeRepository.class;
    }
}
