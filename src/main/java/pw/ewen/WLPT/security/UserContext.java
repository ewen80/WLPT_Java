package pw.ewen.WLPT.security;

import pw.ewen.WLPT.entity.User;

/**
 * Created by wen on 17-2-26.
 */
public interface UserContext {
    User getCurrentUser();
    void setCurrentUser(User user);
}
