package pw.ewen.WLPT.annotations.ResourceType;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wenliang on 17-7-4.
 * 添加了该资源的类将在系统启动时自动在数据库中添加ResourceType记录
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//@Component
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface ResourceType {
    /**
     * 记录在ResourceType中的类全限定名，默认是被标记类的全限定名
     * @return
     */
    String value() default "";
}
