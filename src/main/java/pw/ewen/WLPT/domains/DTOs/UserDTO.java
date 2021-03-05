package pw.ewen.WLPT.domains.DTOs;

import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.RoleRepository;

/**
 * Created by wenliang on 17-4-14.
 */
public class UserDTO {

    private String id;
    private String name;
    private String roleId;
    private String password;
    private String avatar;

    private static class UserConverter implements DTOConvert<UserDTO, User>{
        private RoleRepository roleRepository;

        public UserConverter() {
        }

        public UserConverter(RoleRepository roleRepository) {
            this.roleRepository = roleRepository;
        }

        @Override
        public User doForward(UserDTO dto) {
            Assert.notNull(this.roleRepository);

            Role role = roleRepository.findOne(dto.getRoleId());
            User user = new User(dto.getId(), dto.getName(), dto.getPassword());
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
            dto.setPassword(user.getPassword());
            dto.setAvatar(user.getAvatar());
            if(user.getRole() != null){
                dto.setRoleId(user.getRole().getId());
            }

            return dto;
        }
    }

    /**
     * 转化User对象为UserDTO对象
     * @param user
     * @return
     */
    public static UserDTO convertFromUser(User user){
        UserConverter converter = new UserConverter();
        return converter.doBackward(user);
    }

    /**
     * 转化UserDTO对象为User对象
     */
    public User convertToUser(RoleRepository roleRepository){
        UserConverter converter = new UserConverter(roleRepository);
        return converter.doForward(this);
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

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
