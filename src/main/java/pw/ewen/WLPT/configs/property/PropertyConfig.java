package pw.ewen.WLPT.configs.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wenliang on 17-7-26.
 * 属性文件配置
 */
@Configuration
public class PropertyConfig {

    @Value( "${wlpt.admin.userId}")
    private String defaultAdminUserId;
    @Value( "${wlpt.admin.userName}")
    private String defaultAdminUserName;
    @Value( "${wlpt.admin.password}")
    private String defaultAdminUserPassword;

    @Value( "${wlpt.admin.roleId}")
    private String defaultAdminRoleId;
    @Value( "${wlpt.admin.roleName}")
    private String defaultAdminRoleName;

    @Value( "${wlpt.guest.userId}")
    private String defaultGuestUserId;
    @Value( "${wlpt.guest.userName}")
    private String defaultGuestUserName;
    @Value( "${wlpt.guest.password}")
    private String defaultGuestUserPassword;

    @Value( "${wlpt.guest.roleId}")
    private String defaultGuestRoleId;
    @Value( "${wlpt.guest.roleName}")
    private String defaultGuestRoleName;

    public String getDefaultAdminUserId() {
        return defaultAdminUserId;
    }

    public String getDefaultAdminRoleId() {
        return defaultAdminRoleId;
    }

    public String getDefaultGuestUserId() {
        return defaultGuestUserId;
    }

    public String getDefaultGuestRoleId() {
        return defaultGuestRoleId;
    }

    public String getDefaultAdminUserName() {
        return defaultAdminUserName;
    }

    public String getDefaultAdminRoleName() {
        return defaultAdminRoleName;
    }

    public String getDefaultGuestUserName() {
        return defaultGuestUserName;
    }

    public String getDefaultGuestRoleName() {
        return defaultGuestRoleName;
    }

    public String getDefaultAdminUserPassword() {
        return defaultAdminUserPassword;
    }

    public String getDefaultGuestUserPassword() {
        return defaultGuestUserPassword;
    }
}
