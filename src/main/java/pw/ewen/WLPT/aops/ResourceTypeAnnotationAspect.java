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

    @Around("execution(* pw.ewen.WLPT.domains.entities.Menu.*(..))")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("aoooop");
        return joinPoint.proceed();
    }

    @Before("execution(* pw.ewen.WLPT.repositories.*.*(..))")
    public void tttAOP(){
        System.out.println("aooooop");
    }
}
