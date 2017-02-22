package pw.ewen.WLPT.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Created by wen on 17-2-22.
 * Method级别安全配置
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Override
    @Bean
    protected MethodSecurityExpressionHandler createExpressionHandler(){
        return super.createExpressionHandler();
    }
}
