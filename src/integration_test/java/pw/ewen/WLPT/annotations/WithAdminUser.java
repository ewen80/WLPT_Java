package pw.ewen.WLPT.annotations;

import org.springframework.security.test.context.support.WithSecurityContext;
import pw.ewen.WLPT.configs.security.WithMockAdminRoleSecurityContextFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wen on 17-7-26.
 * 模拟管理员账户进行测试
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockAdminRoleSecurityContextFactory.class)
public @interface WithAdminUser {
}
