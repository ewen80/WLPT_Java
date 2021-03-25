package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.beans.factory.annotation.Autowired;
import pw.ewen.WLPT.domains.DTOs.RoleDTO;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.UserService;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * created by wenliang on 2021-03-24
 * RoleDTO 和 Role 之间转换类
 */
public class RoleDTOConvertor  implements DTOConvert<RoleDTO, Role> {

    @Override
    public Role doForward(RoleDTO roleDTO) {
        Role role = new Role(roleDTO.getId(), roleDTO.getName(), roleDTO.getDescription());
        if(roleDTO.getUsers() != null) {
            Set<User> users = roleDTO.getUsers().stream().map(UserDTO::convertToUser).collect(Collectors.toSet());
            role.setUsers(users);
        }
        return role;
    }

    @Override
    public RoleDTO doBackward(Role role) {
        RoleDTO roleDTO = new RoleDTO(role.getId(), role.getName(), role.getDescription());
        Set<User> users = role.getUsers();
        HashSet<UserDTO> userDTOs = new HashSet<>();
        users.forEach( (User u) -> {
            userDTOs.add(UserDTO.convertFromUser(u));
        });
        roleDTO.setUsers(userDTOs);
        return  roleDTO;
    }
}
