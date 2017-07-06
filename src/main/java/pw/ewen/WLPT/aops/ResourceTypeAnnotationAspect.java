package pw.ewen.WLPT.aops;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by wenliang on 17-7-5.
 */
@Aspect
public class ResourceTypeAnnotationAspect {

    public ResourceTypeAnnotationAspect() {
        System.out.println("created aop");
    }

    @Before("execution(* pw.ewen.WLPT.domains.entities.Menu.new(..))")
    public void tttAOP(){
        System.out.println("menu created");
    }
}
