package pw.ewen.WLPT.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.User;
import pw.ewen.WLPT.exception.domain.DeleteHaveUsersRoleException;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by wen on 17-3-18.
 * 角色仓储测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    //删除有用户的角色
    @Test(expected = DeleteHaveUsersRoleException.class)
    public void deleteHaveUsersRole(){
        Role role = new Role("role1","role1");
        roleRepository.save(role);

        User user = new User("user1","username","password",role);
        userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Basic YWRtaW46YWRtaW4=");

        ResponseEntity<String> response = this.restTemplate.exchange("/roles/role1", HttpMethod.DELETE, new HttpEntity<String>(headers), String.class);
    }
}
