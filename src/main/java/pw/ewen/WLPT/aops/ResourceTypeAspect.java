package pw.ewen.WLPT.aops;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;
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

    @AfterReturning("execution(pw.ewen.WLPT.domains.Resource+.new(..)) && !withincode(pw.ewen.WLPT.aops.ResourceTypeAspect.addResourceTypeInDB)")
    public void saveResourceTypeInDB(JoinPoint joinPoint){

        Class resourceClass = joinPoint.getTarget().getClass();
        String resourceClassName = resourceClass.getCanonicalName();

        System.out.println(resourceClass.toString() + " Created");

        if(this.resourceTypeRepository != null){
            //判断系统中是否已经存在ResourceType,不存在则添加
            if(!this.resourceTypeHadInDB(resourceClassName)){
                //添加ResourceType,考虑是否会造成死循环，因为new ResourceType也会触发切面，此处使用withincode切点
                this.addResourceTypeInDB(resourceClassName);
            }
        }
    }

    /**
     * 该资源类是否已经在数据库中
     * @param resourceTypeClassName 资源类的全限定名
     * @return
     */
    @Cacheable
    private boolean resourceTypeHadInDB(String resourceTypeClassName){
        Assert.notNull(this.resourceTypeRepository);

        ResourceType resourceType = this.resourceTypeRepository.findByClassName(resourceTypeClassName);
        return resourceType != null;
    }

    //在数据库中添加ResourceType信息
    private void addResourceTypeInDB(String resourceTypeClassName){
        Assert.notNull(this.resourceTypeRepository);

        ResourceType menuResourceType = new ResourceType(resourceTypeClassName);
        this.resourceTypeRepository.save(menuResourceType);
    }
}
