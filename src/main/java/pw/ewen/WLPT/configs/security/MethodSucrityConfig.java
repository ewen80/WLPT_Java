package pw.ewen.WLPT.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Created by wen on 17-2-23.
 * 方法安全配置类
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSucrityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired
    private PermissionEvaluator aclPermissionEvaluator;
    @Autowired
    private ApplicationContext context;

    //替代默认hasPermission()
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setPermissionEvaluator(this.aclPermissionEvaluator);
        defaultMethodSecurityExpressionHandler.setApplicationContext(this.context);
        return defaultMethodSecurityExpressionHandler;
    }
}
