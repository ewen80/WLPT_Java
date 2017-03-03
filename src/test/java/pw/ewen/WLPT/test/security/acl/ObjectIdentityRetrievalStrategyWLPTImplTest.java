package pw.ewen.WLPT.test.security.acl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pw.ewen.WLPT.domain.ResourceRange;
import pw.ewen.WLPT.domain.entity.MyResource;
import pw.ewen.WLPT.domain.entity.MyResourceRange;
import pw.ewen.WLPT.repository.MyResourceRangeRepository;
import pw.ewen.WLPT.repository.MyResourceRepository;
import pw.ewen.WLPT.security.acl.ObjectIdentityRetrievalStrategyWLPTImpl;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wenliang on 17-3-3.
 * 测试ACL中获取ObjectIdentity的方法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ObjectIdentityRetrievalStrategyWLPTImplTest {
    @Autowired
    private MyResourceRepository r_repository;
    @Autowired
    private MyResourceRangeRepository rr_repository;
    @Autowired
    private ObjectIdentityRetrievalStrategyWLPTImpl objectIdentityRetrieval;

    @Test
    public void getRightObjectIdentity(){
        MyResource r_number_equal_50 = r_repository.getOne(200L);
        MyResource r_number_equal_100 = r_repository.getOne(201L);

        MyResourceRange rr_number_greater_than_60 = rr_repository.getOne(100L);
        MyResourceRange rr_number_less_than_60 = rr_repository.getOne(101L);

        ObjectIdentity oi = objectIdentityRetrieval.getObjectIdentity(r_number_equal_50);
        assertThat(oi).isEqualTo(new ObjectIdentityImpl(rr_number_less_than_60));
    }
}
