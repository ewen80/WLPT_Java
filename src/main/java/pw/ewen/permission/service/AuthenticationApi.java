package pw.ewen.permission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pw.ewen.permission.config.security.SecurityUserService;
import pw.ewen.permission.repository.UserRepository;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * Created by wenliang on 17-2-13.
 * 用户认证模块
 */
@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationApi {

    @Autowired
    private UserRepository userRepository;

    //用户认证接口
    @RequestMapping(method= RequestMethod.PUT, produces="application/json")
    public boolean checkAuthentication(@RequestBody String userId, String authBase64String){
        UserDetailsService userService = new SecurityUserService(userRepository);

        UserDetails userDetails;
        try{
            userDetails = userService.loadUserByUsername(userId);
            String authString = userDetails.getUsername() + ":" + userDetails.getPassword();
            byte[] encodedBytes = Base64.getEncoder().encode(authString.getBytes());
            String encodedAuthString = new String(encodedBytes);
            return authBase64String.equals(encodedAuthString);

        }catch (Exception exception){
            return false;
        }

    }
}
