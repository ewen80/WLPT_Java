package pw.ewen.WLPT.configs.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;

/**
 * Created by wenliang on 17-4-19.
 * 系统缓存配置
 */
@Configuration
public class EhcacheConfig {

    @Bean
    EhCacheBasedAclCache getAclCache(Ehcache ehcache, PermissionGrantingStrategy permissionGrantingStrategy,
                                     AclAuthorizationStrategy aclAuthorizationStrategy){

        return new EhCacheBasedAclCache(ehcache, permissionGrantingStrategy, aclAuthorizationStrategy);
    }

    @Bean
    EhCacheFactoryBean getEhCache(CacheManager cacheManager){
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(cacheManager);
        ehCacheFactoryBean.setCacheName("aclCache");
//        ehCacheFactoryBean.setTransactionalMode("local");
        return ehCacheFactoryBean;
    }
}
