package pw.ewen.WLPT.aops;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by wenliang on 17-7-5.
 */
@Aspect
@Component
public class  ResourceTypeAnnotationHandler {

    @Around("execution(* pw.ewen.WLPT.domains.entities.Menu.ttt(..))")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("aoooop");
        return joinPoint.proceed();
    }
}
