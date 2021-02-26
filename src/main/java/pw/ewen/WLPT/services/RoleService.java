package pw.ewen.WLPT.services;

<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 3061c9d2cd27923bb33af2f8fb1dff0aa997da04
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.RoleRepository;

<<<<<<< HEAD
import java.util.List;

/**
 * created by wenliang on 20210226
=======
/**
 * created by wenliang on 202102024
>>>>>>> 3061c9d2cd27923bb33af2f8fb1dff0aa997da04
 */
@Service
public class RoleService {

    private RoleRepository roleRepository;

<<<<<<< HEAD
=======
    @Autowired
>>>>>>> 3061c9d2cd27923bb33af2f8fb1dff0aa997da04
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
<<<<<<< HEAD
     * 找到一个角色
     * @param id    角色id
     * @return  角色
     */
    public Role findOne(String id) {
        return this.roleRepository.findOne(id);
    }

    /**
     * 返回所有角色(不分页）
     * @return
     */
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    /**
     * 返回所有角色（分页）
     * @param pr    分页对象
     * @return  角色
     */
    public Page<Role> findAll(PageRequest pr)  {
        return this.roleRepository.findAll(pr);
    }

    /**
     * 保存
     * @param role
     * @return
     */
    public Role save(Role role) {
        return this.roleRepository.save(role);
    }

    /**
     * 通过id删除角色，如果角色下有用户则抛出异常
     * @param ids
     */
    public void delete(String[] ids) {

    }
=======
     * 返回一个角色
     * @param id
     * @return 没有找到返回NULL
     */
    public Role findone(String id) {
        return this.roleRepository.findOne(id);
    }
>>>>>>> 3061c9d2cd27923bb33af2f8fb1dff0aa997da04
}
