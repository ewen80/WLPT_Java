package pw.ewen.WLPT.test.security.acl;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domain.ResourceRange;
import pw.ewen.WLPT.domain.entity.MyResource;
import pw.ewen.WLPT.domain.entity.MyResourceRange;
import pw.ewen.WLPT.repository.MyResourceRangeRepository;
import pw.ewen.WLPT.repository.MyResourceRepository;
import pw.ewen.WLPT.security.acl.ObjectIdentityRetrievalStrategyWLPTImpl;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wenliang on 17-3-3.
 * 测试ACL中获取ObjectIdentity的方法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ObjectIdentityRetrievalStrategyWLPTImplTest {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyResourceRepository r_repository;
    @Autowired
    private MyResourceRangeRepository rr_repository;
    @Autowired
    private ObjectIdentityRetrievalStrategyWLPTImpl objectIdentityRetrieval;

    /**
     * 测试角色对指定资源没有访问权时，从资源获取资源范围策略是否正确
     */
    @Test
    @Transactional
    @WithMockUser(username = "nopoweruser")
    public void haveNoPermissionToResource(){
        MyResource resource = r_repository.getOne(200L);
        ObjectIdentity oi = objectIdentityRetrieval.getObjectIdentity(resource);
        MyResourceRange neverMatchedRange = (new MyResourceRange()).generate_No_Role_Matched_ResourceRange();
        assertThat(oi).isEqualTo(new ObjectIdentityImpl(neverMatchedRange));
    }

    /**
     * 测试角色对指定资源有全部访问权时，从资源获取资源范围策略是否正确
     */
    @Test
    @Transactional
    @WithMockUser(username = "poweruser")
    public void haveAllPermissionToResource(){
        MyResource resource = r_repository.getOne(202L);
        MyResourceRange rr_all = rr_repository.getOne(103L);
        ObjectIdentity oi = objectIdentityRetrieval.getObjectIdentity(resource);

        assertThat(oi).isEqualTo(new ObjectIdentityImpl(rr_all));
    }

    /**
     * 测试角色对指定资源有部分访问权时，从资源获取资源范围策略是否正确
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    public void havePartPermissionToResource(){
        MyResource r_number_equal_50 = r_repository.getOne(200L);
        MyResourceRange rr_number_less_than_60 = rr_repository.getOne(101L);

        ObjectIdentity oi = objectIdentityRetrieval.getObjectIdentity(r_number_equal_50);
        assertThat(oi).isEqualTo(new ObjectIdentityImpl(rr_number_less_than_60));
    }

    private void executeSqlScript(String scriptPath, DataSource dataSource){
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource(scriptPath));
        DatabasePopulatorUtils.execute(resourceDatabasePopulator , dataSource);
    }
}
