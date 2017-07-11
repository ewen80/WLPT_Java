package pw.ewen.WLPT.configs.aop;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import pw.ewen.WLPT.aops.ResourceTypeAspect;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.services.ResourceTypeService;

/**
 * Created by wenliang on 17-7-5.
 */
@Configuration
@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public ResourceTypeAspect resourceTypeAnnotationAspect(ResourceTypeService resourceTypeService){
        ResourceTypeAspect aspect = Aspects.aspectOf(ResourceTypeAspect.class);
        aspect.setResourceTypeService(resourceTypeService);
        return aspect;
    }
}
