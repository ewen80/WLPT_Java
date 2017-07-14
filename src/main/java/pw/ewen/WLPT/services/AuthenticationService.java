package pw.ewen.WLPT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pw.ewen.WLPT.repositories.UserRepository;

import java.util.Base64;
import java.util.HashMap;

/**
 * Created by wenliang on 17-7-14.
 * 认证服务
 */
@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    //用户认证接口
    public boolean checkAuthentication(HashMap<String,String> authInfo){
        UserDetails userDetails;
        try{
            userDetails = this.userDetailsService.loadUserByUsername(authInfo.get("userId"));
            String authString = userDetails.getUsername() + ":" + userDetails.getPassword();
            byte[] encodedBytes = Base64.getEncoder().encode(authString.getBytes());
            String encodedAuthString = new String(encodedBytes);
            return authInfo.get("authToken").equals(encodedAuthString);

        }catch (Exception exception){
            return false;
        }

    }
}
