package pw.ewen.WLPT.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import pw.ewen.WLPT.repositories.UserRepository;

/**
 * Created by wen on 17-2-8.
 * Spring Security 配置类
 * Web级别
 */
@Configuration
@EnableWebSecurity  //启用web安全性
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();//Disable X-Frame-Options in Spring Security,用于H2 Console

        //TODO: 更换jwt认证方式  20170210
        //      采用BASIC AUTHENTICATION主要是为了方便快速开发
        //      后期考虑换成JWT方式，因为session-cookie方式，只适合客户端是浏览器的情况
        http
            .authorizeRequests().antMatchers("/h2console/**").permitAll()   //对嵌入式数据库console不做用户认证
//                                .antMatchers("/authentication").permitAll() //对认证服务不做用户认证
                                .antMatchers("/onceinit").permitAll() //对系统首次初始化请求不做用户认证
                                .anyRequest().authenticated()                            //其他访问都需要经过认证
                            .and()
                                .httpBasic()   //Basic Authentication 认证方式
                            .and()
                            .logout();


        http.csrf().disable(); //关闭CSRF检查
        http.cors();//允许CORS跨域请求

    }
}
