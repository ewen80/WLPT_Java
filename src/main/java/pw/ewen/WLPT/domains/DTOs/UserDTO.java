package pw.ewen.WLPT.domains.DTOs;

import pw.ewen.WLPT.domains.dtoconvertors.UserDTOConvertor;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.RoleService;

/**
 * Created by wenliang on 17-4-14.
 */
public class UserDTO {

    private String id;
    private String name;
    private String roleId;
    private String passwordMD5;
    private String avatar = "";

    /**
     * 转化User对象为UserDTO对象
     * @param user 用户对象
     * @return 用户DTO对象
     */
    public static UserDTO convertFromUser(User user){
        return new UserDTOConvertor().toDTO(user);
    }

    /**
     * 转化UserDTO对象为User对象
     */
    public User convertToUser(RoleService roleService){
        UserDTOConvertor converter = new UserDTOConvertor();
        return converter.toUser(this, roleService);
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

    public String getPasswordMD5() {
        return passwordMD5;
    }

    public void setPasswordMD5(String passwordMD5) {
        this.passwordMD5 = passwordMD5;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
