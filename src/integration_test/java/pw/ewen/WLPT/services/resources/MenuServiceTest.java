package pw.ewen.WLPT.services.resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import pw.ewen.WLPT.domains.entities.resources.Menu;
import pw.ewen.WLPT.repositories.resources.MenuRepository;
import pw.ewen.WLPT.repositories.specifications.core.SearchCriteria;
import pw.ewen.WLPT.repositories.specifications.core.SearchOperation;
import pw.ewen.WLPT.repositories.specifications.core.SearchSpecification;
import pw.ewen.WLPT.services.resources.MenuService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wen on 17-6-12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MenuServiceTest {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuService menuService;

    private Menu menu1;

    private Menu menu11;

    private Menu menu111;
    private Menu menu112;

    private Menu menu12;

    private Menu menu121;
    private Menu menu122;

    private Menu menu13;

    private Menu menu131;
    private Menu menu132;
    private Menu menu133;

    private Menu menu2;

    private Menu menu21;
    private Menu menu22;
    private Menu menu23;

    private Menu menu3;

    private Menu menu31;
    private Menu menu32;
    private Menu menu33;

    @BeforeTransaction
    public void beforeTransaction(){
        this.menu1 = new Menu("1");
        this.menu2 = new Menu("2");
        this.menu3 = new Menu("3");

        this.menu1.setOrderId(1);
        this.menu2.setOrderId(2);
        this.menu3.setOrderId(3);

        this.menuRepository.save(menu1);
        this.menuRepository.save(menu2);
        this.menuRepository.save(menu3);

        /////////////////////////////////////////////

        this.menu11 = new Menu("11");
        this.menu12 = new Menu("12");
        this.menu13 = new Menu("13");

        this.menu11.setParent(this.menu1);
        this.menu11.setOrderId(1);

        this.menu12.setParent(this.menu1);
        this.menu12.setOrderId(2);

        this.menu13.setParent(this.menu1);
        this.menu13.setOrderId(3);

        this.menuRepository.save(menu11);
        this.menuRepository.save(menu12);
        this.menuRepository.save(menu13);

        /////////////////////////////////////////////////////

        this.menu111 = new Menu("111");
        this.menu112 = new Menu("112");

        this.menu111.setParent(this.menu11);
        this.menu111.setOrderId(1);

        this.menu112.setParent(this.menu11);
        this.menu112.setOrderId(2);

        this.menuRepository.save(menu111);
        this.menuRepository.save(menu112);

        /////////////////////////////////////////////////////

        this.menu121 = new Menu("121");
        this.menu122 = new Menu("122");

        this.menu121.setParent(this.menu12);
        this.menu121.setOrderId(1);

        this.menu122.setParent(this.menu12);
        this.menu122.setOrderId(2);

        this.menuRepository.save(menu121);
        this.menuRepository.save(menu122);

        ////////////////////////////////////////////////////////

        this.menu131 = new Menu("131");
        this.menu132 = new Menu("132");
        this.menu133 = new Menu("133");

        this.menu131.setParent(this.menu13);
        this.menu131.setOrderId(1);

        this.menu132.setParent(this.menu13);
        this.menu132.setOrderId(2);

        this.menu133.setParent(this.menu13);
        this.menu133.setOrderId(3);

        this.menuRepository.save(menu131);
        this.menuRepository.save(menu132);
        this.menuRepository.save(menu133);

        //////////////////////////////////////////////

        this.menu21 = new Menu("21");
        this.menu22 = new Menu("22");
        this.menu23 = new Menu("23");

        this.menu21.setParent(this.menu2);
        this.menu21.setOrderId(1);

        this.menu22.setParent(this.menu2);
        this.menu22.setOrderId(2);

        this.menu23.setParent(this.menu2);
        this.menu23.setOrderId(3);

        this.menuRepository.save(menu21);
        this.menuRepository.save(menu22);
        this.menuRepository.save(menu23);

        //////////////////////////////////////////////
        this.menu31 = new Menu("31");
        this.menu32 = new Menu("32");
        this.menu33 = new Menu("33");

        this.menu31.setParent(this.menu3);
        this.menu31.setOrderId(1);

        this.menu32.setParent(this.menu3);
        this.menu32.setOrderId(2);

        this.menu33.setParent(this.menu3);
        this.menu33.setOrderId(3);

        this.menuRepository.save(menu31);
        this.menuRepository.save(menu32);
        this.menuRepository.save(menu33);
    }

    @AfterTransaction
    public void afterTransaction(){
        List<Menu> delMenus = Arrays.asList(this.menu1,this.menu2,this.menu3,
                                            this.menu11,this.menu12,this.menu13,
                                            this.menu21,this.menu22,this.menu23,
                                            this.menu31,this.menu32,this.menu33,
                                            this.menu111,this.menu112,
                                            this.menu121,this.menu122,
                                            this.menu131,this.menu132,this.menu133);
        this.menuRepository.deleteInBatch(delMenus);
    }

    /**
     * |-menu1
     * |  |-menu11
     * |  |   |-menu111
     * |  |   |-menu112
     * |  |-menu12
     * |-menu2
     */
    @Test
    public void generateUpflowTree(){
        List<Menu> leafMenus;
        SearchSpecification spec111 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.EQUALITY, "111"));
        SearchSpecification spec112 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.EQUALITY, "112"));
        SearchSpecification spec2 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.EQUALITY, "2"));

        leafMenus = Arrays.asList(this.menuRepository.findOne(spec111),
                        this.menuRepository.findOne(spec112),
                        this.menuRepository.findOne(spec2));


        List<Menu> menus = this.menuService.generateUpflowTree(leafMenus);

        assertThat(menus)
                .hasSize(2)
                .extracting("name")
                    .containsExactlyInAnyOrder("1", "2");
//                    menu1
//                     |-menu11
//                * |  |   |-menu111
//                * |  |   |-menu112
        assertThat(menus.get(0).getChildren())
                .hasSize(1)
                .extracting("name")
                    .containsExactlyInAnyOrder("11");
//                     |-menu11
//                * |  |   |-menu111
//                * |  |   |-menu112
        assertThat(menus.get(0).getChildren().get(0).getChildren())
                .hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder("111", "112");
    }

    /**
     * 测试没有任何权限设置过的情况下，生成叶子节点
     */
    @Test
    @WithMockUser(value = "admin")
    public void testGeneratePermissionLeafMenus_noAuthorizedMenus(){
        SearchSpecification spec1 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.EQUALITY, "1"));
        SearchSpecification spec2 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.EQUALITY, "2"));
        SearchSpecification spec3 = new SearchSpecification(
                new SearchCriteria("name", SearchOperation.EQUALITY, "3"));

        List<Menu> menus = this.menuService.generatePermissionLeafMenus(Arrays.asList(this.menuRepository.findOne(spec1),
                                                                                    this.menuRepository.findOne(spec2),
                                                                                    this.menuRepository.findOne(spec3)));
        assertThat(menus)
                .hasSize(16);
    }


}
