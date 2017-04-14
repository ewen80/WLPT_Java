package pw.ewen.WLPT.domains.DTOs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.entities.ResourceRange;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.repositories.RoleRepository;
import pw.ewen.WLPT.repositories.UserRepository;

/**
 * Created by wenliang on 17-4-14.
 */
public class UserDTO {

    private String id;
    private String name;
    private String roleId;
    private String password;
    private String picture;

    private static class UserConverter implements DTOConvert<UserDTO, User>{
        private RoleRepository roleRepository;
        private UserRepository userRepository;

        private User user;


        public UserConverter(User user){
            this.user = user;
        }

        public UserConverter(RoleRepository roleRepository, UserRepository userRepository) {
            this.roleRepository = roleRepository;
            this.userRepository = userRepository;
        }

        @Override
        public User doForward(UserDTO dto) {
            Assert.notNull(this.roleRepository);
            Assert.notNull(this.userRepository);

            Role role = roleRepository.getOne(dto.getRoleId());
            User user = new User(dto.getId(), dto.getName(), dto.getPassword(), role);

            return user;
        }

        @Override
        public UserDTO doBackward(User user) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setPassword(user.getPassword());
            dto.setPicture(user.getPassword());
            dto.setRoleId(user.getRole().getId());
            return dto;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
