package pw.ewen.WLPT.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;
import pw.ewen.WLPT.annotations.WithAdminUser;
import pw.ewen.WLPT.configs.property.PropertyConfig;

/**
 * Created by wen on 17-7-26.
 * 提供 @WithMockAdminRole 使用的SecurityContextFactory
 */
public class WithMockAdminRoleSecurityContextFactory implements WithSecurityContextFactory<WithAdminUser> {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PropertyConfig propertyConfig;

    @Override
    public SecurityContext createSecurityContext(WithAdminUser adminUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetails principal = userDetailsService.loadUserByUsername(propertyConfig.getDefaultAdminUserId());
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
