package pw.ewen.WLPT.domains.dtoconvertors;

import org.springframework.beans.factory.annotation.Autowired;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.RoleService;

/**
 * created by wenliang on 2021-03-24
 * UserDTO 和 User 转换类
 */
public class UserDTOConvertor implements DTOConvert<UserDTO, User> {

    @Autowired
    private RoleService roleService;

    @Override
    public User doForward(UserDTO dto) {
        Role role = roleService.findOne(dto.getRoleId());
        User user = new User(dto.getId(), dto.getName(), dto.getPasswordMD5(), role);
        if(!dto.getAvatar().isEmpty()) {
            user.setAvatar(dto.getAvatar());
        }

        return user;
    }

    @Override
    public UserDTO doBackward(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPasswordMD5(user.getPasswordMD5());
        dto.setAvatar(user.getAvatar());
        if(user.getRole() != null){
            dto.setRoleId(user.getRole().getId());
        }

        return dto;
    }
}
