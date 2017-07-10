package pw.ewen.WLPT.configs.security;

import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;


/**
 * Created by wen on 17-2-22.
 * Acl配置
 */
@Configuration
public class SecurityAclConfig {

    @Bean
    PermissionEvaluator getAclPermissionEvaluator(AclService aclService, ObjectIdentityRetrievalStrategy objectIdentityRetrievalStrategy){
        AclPermissionEvaluator aclPermissionEvaluator = new AclPermissionEvaluator(aclService);
        aclPermissionEvaluator.setObjectIdentityRetrievalStrategy(objectIdentityRetrievalStrategy);
        return aclPermissionEvaluator;
    }

    @Bean
    AclPermissionCacheOptimizer getAclPermissionCacheOptimizer(AclService aclService){
        return new AclPermissionCacheOptimizer(aclService);
    }

    @Bean
    JdbcMutableAclService getAclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache){
        return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
    }

    @Bean
    BasicLookupStrategy getLookupStrategy(DataSource dataSource, AclCache aclCache,
                                          AclAuthorizationStrategy aclAuthorizationStrategy,
                                          AuditLogger auditLogger){
        return new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy, auditLogger);
    }

    @Bean
    DefaultPermissionGrantingStrategy getPermissionGrantingStrategy(AuditLogger auditLogger){
        return new DefaultPermissionGrantingStrategy(auditLogger);
    }

    @Bean
    AuditLogger getAuditLogger(){
        return new ConsoleAuditLogger();
    }

    @Bean
    AclAuthorizationStrategy getAclAuthorizationStrategy(GrantedAuthority... auths){
        return new AclAuthorizationStrategyImpl(auths);
    }

    /**
     * 有管理ACL权限的角色，默认：admin组
     * @return
     */
    @Bean
    SimpleGrantedAuthority getGrantedAuthority(){
        return new SimpleGrantedAuthority("admin");
    }

}
