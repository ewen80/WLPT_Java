package pw.ewen.WLPT.aops;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;
import pw.ewen.WLPT.services.ResourceTypeService;

/**
 * Created by wenliang on 17-7-5.
 * Resource资源创建AOP切面
 * 在资源创建前判断该ResourceType是否已经保存，如果没有则保存
 */
@Aspect
public class ResourceTypeAspect {

    private ResourceTypeService resourceTypeService;

    public void setResourceTypeService(ResourceTypeService resourceTypeService) {
        this.resourceTypeService = resourceTypeService;
    }

    @Pointcut("execution(pw.ewen.WLPT.domains.Resource+.new(..))")
    private void resourceConstructor(){}

    @Pointcut("!cflow(execution(* pw.ewen.WLPT..*.ResourceTypeService.save(String)))")
    private void noRecursive(){}


    @Before("resourceConstructor() && noRecursive()")
    public void saveResourceTypeInDB(JoinPoint joinPoint){

        String resourceClassName = joinPoint.getTarget().getClass().getCanonicalName();
        if(this.resourceTypeService != null){
            //判断系统中是否已经存在ResourceType,不存在则添加
            this.resourceTypeService.initialResourceTypeInDB(resourceClassName);
        }
    }



}
