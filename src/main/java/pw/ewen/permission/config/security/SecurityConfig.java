package pw.ewen.permission.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pw.ewen.permission.repository.UserRepository;

import javax.sql.DataSource;

/**
 * Created by wen on 17-2-8.
 * Spring Security 配置类
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(new SecurityUserService(userRepository));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();//Disable X-Frame-Options in Spring Security

        http
            .authorizeRequests().antMatchers("/h2console/**").permitAll()//对嵌入式数据库console不做安全检查
                                .anyRequest().authenticated()
                            .and()
                                .httpBasic();
        http.csrf().disable(); //关闭CSRF检查

    }
}
