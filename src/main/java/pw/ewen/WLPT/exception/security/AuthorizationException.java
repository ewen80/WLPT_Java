package pw.ewen.WLPT.exception.security;

/**
 * Created by wen on 17-3-6.
 * 授权操作中的错误
 *
 */
public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
