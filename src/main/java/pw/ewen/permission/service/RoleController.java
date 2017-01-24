package pw.ewen.permission.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.permission.entity.Role;
import pw.ewen.permission.repository.RoleRepository;

import java.util.List;


@RestController
@RequestMapping(value = "/roles")
public class RoleController {
    private RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public List<Role> getAllRoles(){
        return  this.roleRepository.findAll();
    }

    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public Role save(@RequestBody Role role){
        return this.roleRepository.save(role);
    }
}
