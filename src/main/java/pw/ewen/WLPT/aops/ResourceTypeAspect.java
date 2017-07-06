package pw.ewen.WLPT.aops;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import pw.ewen.WLPT.domains.entities.ResourceType;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

/**
 * Created by wenliang on 17-7-5.
 * Resource资源创建AOP切面
 * 在资源创建前判断该ResourceType是否已经保存，如果没有则保存
 */
@Aspect
public class ResourceTypeAspect {

    private ResourceTypeRepository resourceTypeRepository;

    public void setResourceTypeRepository(ResourceTypeRepository resourceTypeRepository) {
        this.resourceTypeRepository = resourceTypeRepository;
    }

    @Before("execution(pw.ewen.WLPT.domains.Resource+.new(..))")
    public void saveResourceTypeInDB(JoinPoint joinPoint){
        Class resourceClass = joinPoint.getTarget().getClass();
        System.out.println(resourceClass.toString() + " Created");

        //判断系统中是否已经存在ResourceType,不存在则添加
//        ResourceType resourceType = this.resourceTypeRepository.findByClassName(resourceClass.getCanonicalName());
//        if(resourceType == null){
//            //添加ResourceType,考虑是否会造成死循环，因为new ResourceType也会出发切面
//
//        }
    }
}
