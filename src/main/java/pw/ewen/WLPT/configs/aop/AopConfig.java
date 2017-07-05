package pw.ewen.WLPT.configs.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import pw.ewen.WLPT.aops.ResourceTypeAnnotationHandler;

/**
 * Created by wenliang on 17-7-5.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan
public class AopConfig {

    @Bean
    public ResourceTypeAnnotationHandler resourceTypeAnnotationHandler(){
        return new ResourceTypeAnnotationHandler();
    }
}
