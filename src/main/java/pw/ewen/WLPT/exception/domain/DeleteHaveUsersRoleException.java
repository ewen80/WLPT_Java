package pw.ewen.WLPT.exception.domain;

/**
 * Created by wen on 17-3-18.
 * 删除还存在用户的角色错误
 */
public class DeleteHaveUsersRoleException extends RuntimeException {
    public DeleteHaveUsersRoleException(String message) {
        super(message);
    }
}
