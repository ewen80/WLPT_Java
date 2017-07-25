package pw.ewen.WLPT.test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.ResourceRangeRepository;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.services.ResourceRangeService;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by wenliang on 17-4-12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ResourceRangeServiceTest {

    @Autowired
    private ResourceRangeService resourceRangeService;
    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private ResourceRangeRepository resourceRangeRepository;
    @Autowired
    private RoleRepository roleRepository;

    private ResourceType type1;
    private ResourceType type2;

    private Role role1;
    private Role role2;

    private ResourceRange range1;
    private ResourceRange range2;
    private ResourceRange range3;

    @Before
    public void init(){
        this.type1 = new ResourceType("className1", "typeName1", "");
        this.resourceTypeRepository.save(this.type1);
        this.type2 = new ResourceType("className2", "typeName2", "");
        this.resourceTypeRepository.save(this.type2);

        this.role1 = new Role("role1","role1");
        this.roleRepository.save(this.role1);
        this.role2 = new Role("role2","role2");
        this.roleRepository.save(this.role2);

        this.range1 = new ResourceRange("filter1", this.role1, this.type1);
        this.resourceRangeRepository.save(this.range1);
        this.range2 = new ResourceRange("filter2", this.role2, this.type1);
        this.resourceRangeRepository.save(this.range2);
        this.range3 = new ResourceRange("filter3", this.role1, this.type2);
        this.resourceRangeRepository.save(this.range3);

    }

    @Test
    public void testGetByResourceType(){
        List<ResourceRange> ranges = this.resourceRangeService.getByResourceType(this.type1.getId());
        assertThat(ranges)
                .hasSize(2)
                .extracting("Role.roleId")
                .containsOnly("role1","role2");
    }
}
