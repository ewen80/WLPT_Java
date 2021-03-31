package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.RoleService;

/**
 * created by wenliang on 2021-03-24
 * UserDTO 和 User 转换类
 */
public class UserDTOConvertor {

    public User toUser(UserDTO dto, RoleService roleService) {

        Role role = roleService.findOne(dto.getRoleId());
        User user = new User(dto.getId(), dto.getName(), role);
        if(!dto.getAvatar().isEmpty()) {
            user.setAvatar(dto.getAvatar());
        }

        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAvatar(user.getAvatar());
        if(user.getRole() != null){
            dto.setRoleId(user.getRole().getId());
        }

        return dto;
    }
}
