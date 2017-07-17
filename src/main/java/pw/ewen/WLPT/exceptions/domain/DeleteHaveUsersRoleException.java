package pw.ewen.WLPT.exceptions.domain;

/**
 * Created by wen on 17-3-18.
 * 删除还存在用户的角色错误
 */
public class DeleteHaveUsersRoleException extends RuntimeException {
    public DeleteHaveUsersRoleException(String roleId) {
        super("试图删除角色roleId: " + roleId + "  失败，因为该角色下有用户");
    }
}
