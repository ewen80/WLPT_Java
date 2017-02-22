package pw.ewen.WLPT.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionCacheOptimizer;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Created by wen on 17-2-22.
 * Acl配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityAclConfig {
    @Autowired
    AclPermissionCacheOptimizer aclPermissionCacheOptimizer;
    @Autowired
    AclPermissionEvaluator aclPermissionEvaluator;

    @Bean
    MethodSecurityExpressionHandler getMethodSecurityExpressionHandler(){
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =  new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setPermissionCacheOptimizer(aclPermissionCacheOptimizer);
        defaultMethodSecurityExpressionHandler.setPermissionEvaluator(aclPermissionEvaluator);
        return defaultMethodSecurityExpressionHandler;
    }
}
