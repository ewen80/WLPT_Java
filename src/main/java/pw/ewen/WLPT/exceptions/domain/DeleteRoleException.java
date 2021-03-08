package pw.ewen.WLPT.exceptions.domain;

/**
 * Created by wen on 17-3-18.
 * 删除还存在用户的角色错误
 */
public class DeleteRoleException extends RuntimeException {
    public DeleteRoleException(String message) {
        super(message);
    }
}
