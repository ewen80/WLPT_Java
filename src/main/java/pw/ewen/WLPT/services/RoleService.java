package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.repositories.RoleRepository;

/**
 * created by wenliang on 202102024
 */
@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * 返回一个角色
     * @param id
     * @return 没有找到返回NULL
     */
    public Role findone(String id) {
        return this.roleRepository.findOne(id);
    }
}
