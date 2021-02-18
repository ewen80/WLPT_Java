package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.repositories.UserRepository;

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
    private UserDetailsService userDetailsService;

    //用户认证接口
    @RequestMapping(method= RequestMethod.GET, produces="application/json")
    public boolean authenticate(@RequestParam(value = "userid", defaultValue = "") String userid,
                                @RequestParam(value = "token", defaultValue = "") String authToken){
        UserDetails userDetails;
        try{
            userDetails = userDetailsService.loadUserByUsername(userid);
            String authString = userDetails.getUsername() + ":" + userDetails.getPassword();
            byte[] encodedBytes = Base64.getEncoder().encode(authString.getBytes());
            String encodedAuthString = new String(encodedBytes);
            return authToken.equals(encodedAuthString);

        }catch (Exception exception){
            return false;
        }

    }
}
