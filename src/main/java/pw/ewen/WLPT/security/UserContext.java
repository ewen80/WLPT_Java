package pw.ewen.WLPT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.repositories.UserRepository;

/**
 * Created by wen on 17-2-26.
 * 当前登录用户上下文
 */
@Component
public class UserContext {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserContext(UserRepository userRepository, UserDetailsService userDetailsService) {
        Assert.notNull(userRepository, "userRepository cannot be null");
        Assert.notNull(userDetailsService, "userDetailsService cannot be null");

        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    //没有找到用户返回null
    public User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }
        String userId = authentication.getName();
        if (userId == null) {
            return null;
        }
        User user = userRepository.findOne(userId);
//        if (user == null) {
//            throw new IllegalStateException(
//                    "Could not find user with id " + userId);
//        }
        return user;
    }

    public void setCurrentUser(User user) {
        Assert.notNull(user, "user cannot be null");

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getId());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                user.getPasswordMD5(),userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
