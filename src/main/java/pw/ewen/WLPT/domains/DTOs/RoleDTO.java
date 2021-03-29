package pw.ewen.WLPT.domains.DTOs;

import pw.ewen.WLPT.domains.dtoconvertors.RoleDTOConvertor;
import pw.ewen.WLPT.domains.entities.Role;
import pw.ewen.WLPT.services.RoleService;

import java.util.HashSet;
import java.util.Set;

/**
 * created by wenliang on 2021-03-17
 */
public class RoleDTO {

    private String id;
    private String name;
    private String description;
    private Set<UserDTO> users = new HashSet<>();

    public RoleDTO() {  }

    public RoleDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDTO(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public static RoleDTO convertFromRole(Role role) {
        RoleDTOConvertor converter = new RoleDTOConvertor();
        return converter.toDTO(role);
    }

    public static Role convertToRole(RoleDTO roleDTO, RoleService roleService) {
        RoleDTOConvertor converter = new RoleDTOConvertor();
        return converter.toRole(roleDTO, roleService);
    }
}
