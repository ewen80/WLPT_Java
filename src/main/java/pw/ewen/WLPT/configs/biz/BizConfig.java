package pw.ewen.WLPT.configs.biz;

/**
 * created by wenliang on 2021-03-22
 * 业务类的配置文件
 */
public class BizConfig {

    // 管理员角色id
    public static String getAdminRoleId() {
        return "admin";
    }

    // 匿名角色id
    public static String getAnonymousRoleId() {
        return "anonymous";
    }

    // 管理员用户id
    public static String getAdminUserId() {
        return "admin";
    }

    // 游客用户id
    public  static String getGuestUserId() {
        return "guest";
    }
}
