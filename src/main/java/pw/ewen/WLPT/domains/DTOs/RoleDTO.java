package pw.ewen.WLPT.domains.DTOs;

import pw.ewen.WLPT.domains.entities.Role;

/**
 * created by wenliang on 2021-03-17
 */
public class RoleDTO {

    private String id;
    private String name;
    private String description;

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

    private static class RoleConverter implements DTOConvert<RoleDTO, Role> {
        public RoleConverter() {
        }

        @Override
        public Role doForward(RoleDTO roleDTO) {
            return new Role(roleDTO.getId(), roleDTO.getName(), roleDTO.getDescription());
        }

        @Override
        public RoleDTO doBackward(Role role) {
            return new RoleDTO(role.getId(), role.getName(), role.getDescription());
        }
    }

    public static RoleDTO convertFromRole(Role role) {
        RoleConverter converter = new RoleConverter();
        return converter.doBackward(role);
    }

    public static Role convertToRole(RoleDTO roleDTO) {
        RoleConverter converter = new RoleConverter();
        return converter.doForward(roleDTO);
    }
}
