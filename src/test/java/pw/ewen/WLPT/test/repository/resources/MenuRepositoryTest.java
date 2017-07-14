package pw.ewen.WLPT.test.repository.resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pw.ewen.WLPT.domains.entities.Menu;
import pw.ewen.WLPT.repositories.MenuRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wenliang on 17-5-9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    private Menu menu1,menu11;

    @Before
    public void init() {
        menu1 = new Menu();
        menu1.setName("menu1");
        menu1.setPath("path1");
        menu1.setOrderId(1);

        menu1 = this.menuRepository.save(menu1);

        menu11 = new Menu();
        menu11.setName("menu11");
        menu11.setPath("path11");
        menu11.setParent(menu1);

        menu11 = this.menuRepository.save(menu11);
    }

    /**
     * 测试给定父id获取子菜单
     */
    @Test
    public void testGetByParentId() {
        List<Menu> menus = this.menuRepository.findByParent_id(this.menu1.getId());
        assertThat(menus).hasSize(1)
                .containsOnly(menu11);
    }
}
