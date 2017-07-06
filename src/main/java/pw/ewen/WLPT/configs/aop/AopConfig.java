package pw.ewen.WLPT.configs.aop;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import pw.ewen.WLPT.aops.ResourceTypeAspect;

/**
 * Created by wenliang on 17-7-5.
 */
@Configuration
//@EnableSpringConfigured
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
//@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AopConfig {

    @Bean
    public ResourceTypeAspect resourceTypeAnnotationAspect(){
        return Aspects.aspectOf(ResourceTypeAspect.class);
    }
}
