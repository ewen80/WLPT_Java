package pw.ewen.WLPT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pw.ewen.WLPT.entity.User;
import pw.ewen.WLPT.repository.UserRepository;

/**
 * Created by wen on 17-2-26.
 * 当前登录用户上下文
 */
public class UserContextImpl implements UserContext {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserContextImpl(UserRepository userRepository,UserDetailsService userDetailsService) {
        if (userRepository == null) {
            throw new IllegalArgumentException("userRepository cannot be null");
        }
        if (userDetailsService == null) {
            throw new IllegalArgumentException("userDetailsService cannot be null");
        }
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }


    @Override
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
        if (user == null) {
            throw new IllegalStateException(
                    "Could not find user with id " + userId);
        }
        return user;
    }

    @Override
    public void setCurrentUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getId());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                user.getPassword(),userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
