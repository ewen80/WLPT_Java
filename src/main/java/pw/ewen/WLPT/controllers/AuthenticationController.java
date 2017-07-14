package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pw.ewen.WLPT.repositories.UserRepository;
import pw.ewen.WLPT.services.AuthenticationService;

import java.util.Base64;
import java.util.HashMap;

/**
 * Created by wenliang on 17-2-13.
 * 用户认证
 */
@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    //用户认证接口
    @RequestMapping(method= RequestMethod.PUT, produces="application/json")
    public boolean checkAuthentication(@RequestBody HashMap<String,String> authInfo){
        return this.authenticationService.checkAuthentication(authInfo);
    }
}
