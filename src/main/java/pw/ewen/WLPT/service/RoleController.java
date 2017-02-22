package pw.ewen.WLPT.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.entity.Role;
import pw.ewen.WLPT.repository.RoleRepository;
import pw.ewen.WLPT.repository.specifications.RoleSpecifications;

import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {
    private RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

//    @RequestMapping(value="/all", method=RequestMethod.GET, produces="application/json")
//    public List<Role> getAllRoles(){
//        return  this.roleRepository.findAll();
//    }

    //获取角色（分页）
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public Page<Role> getRolesWithPage(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                       @RequestParam(value = "pageSize", defaultValue = "20") int pageSize){
//        Specification<Role> spec = RoleSpecifications.hasName("ad");
        return roleRepository.findAll(new PageRequest(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "name")));
    }

    @RequestMapping(value="/{roleId}", method=RequestMethod.GET, produces="application/json")
    public Role getOneRole(@PathVariable("roleId") String roleId){
        return roleRepository.findOne(roleId);
    }

//    @RequestMapping(value = "/byname/{name}", method=RequestMethod.GET, produces = "application/json")
//    public List<Role> getRolesByName(@PathVariable("name") String name){
//        return roleRepository.findByName(name);
//    }

    @RequestMapping(method=RequestMethod.POST, produces = "application/json")
    public Role save(@RequestBody Role role){
        return this.roleRepository.save(role);
    }

    @RequestMapping(value = "/{roleIds}", method=RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable("roleIds") String roleIds){
        String[] arrRoleIds = roleIds.split(",");
        for(String id : arrRoleIds){
            this.roleRepository.delete(id);
        }

    }
}
