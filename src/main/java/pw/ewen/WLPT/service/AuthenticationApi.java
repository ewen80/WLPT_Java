package pw.ewen.WLPT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.config.security.SecurityUserService;
import pw.ewen.WLPT.repository.UserRepository;
import java.util.Base64;
import java.util.HashMap;

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
    public boolean checkAuthentication(@RequestBody HashMap<String,String> authInfo){

        UserDetailsService userService = new SecurityUserService(userRepository);

        UserDetails userDetails;
        try{
            userDetails = userService.loadUserByUsername(authInfo.get("userId"));
            String authString = userDetails.getUsername() + ":" + userDetails.getPassword();
            byte[] encodedBytes = Base64.getEncoder().encode(authString.getBytes());
            String encodedAuthString = new String(encodedBytes);
            return authInfo.get("authToken").equals(encodedAuthString);

        }catch (Exception exception){
            return false;
        }

    }
}
