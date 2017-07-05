package pw.ewen.WLPT.annotations.ResourceType;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import pw.ewen.WLPT.repositories.ResourceTypeRepository;

/**
 * Created by wenliang on 17-7-5.
 */
@Component
public class ResourceTypeAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    /**
     * 检查是否已经有该类的ResourceType，如果没有则新建
     */
    public void generateResourceType(Class<?> clazz){
        //检查类上是否有@ResourceType注解
        if(clazz.isAnnotationPresent(ResourceType.class)){
            String className;
            String classSimpleName;
            ResourceType resourceTypeAnnoation = (ResourceType) clazz.getAnnotation(ResourceType.class);
            //注解value是否为空，如果不为空，则按照value值保存resourceType.className，否则使用默认类的全限定名
            if(resourceTypeAnnoation.value().isEmpty()){
                //空，默认使用类全限定名
                className = clazz.getCanonicalName();
                classSimpleName = clazz.getSimpleName();
            } else {
                //不空，使用value作为resourceType的className
                classSimpleName = className = resourceTypeAnnoation.value();
            }
            //判断相应的ResourceType是否已經存在
            if(this.resourceTypeRepository.findByClassName(className) == null){
                //不存在则保存
                pw.ewen.WLPT.domains.entities.ResourceType resourceType = new pw.ewen.WLPT.domains.entities.ResourceType(className, classSimpleName);
                this.resourceTypeRepository.save(resourceType);
            }
        }
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        this.generateResourceType(bean.getClass());
        return bean;
    }
}
