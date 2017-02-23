package pw.ewen.WLPT.config.security;

import net.sf.ehcache.Ehcache;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.ehcache.CacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.activation.DataSource;

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
    @Autowired
    DataSource dataSource;
    @Autowired
    CacheManager aclCacheManager;
    @Autowired
    Ehcache aclEhCacheFactoryBean;
    @Autowired
    EhCacheBasedAclCache aclCache;

    @Bean
    MethodSecurityExpressionHandler getMethodSecurityExpressionHandler(){
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =  new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setPermissionCacheOptimizer(aclPermissionCacheOptimizer);
        defaultMethodSecurityExpressionHandler.setPermissionEvaluator(aclPermissionEvaluator);
        return defaultMethodSecurityExpressionHandler;
    }

    @Bean
    JdbcMutableAclService getJdbcMutableAclService(){
        new JdbcMutableAclService(dataSource,)
    }

    @Bean
    BasicLookupStrategy getBasicLookupStrategy(){
        return new BasicLookupStrategy(dataSource, )
    }

    @Bean
    EhCacheBasedAclCache getAclCache(){
        return new EhCacheBasedAclCache()
    }

    @Bean
    Ehcache getEhCacheFactoryBean(){
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(aclCacheManager);
        ehCacheFactoryBean.setCacheName("aclCache");
        return ehCacheFactoryBean.getObject();
    }

    @Bean
    CacheManager getAclCacheManager(){
        return new EhCacheManagerFactoryBean().getObject();
    }
}
