package pw.ewen.WLPT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pw.ewen.WLPT.domains.DTOs.UserDTO;
import pw.ewen.WLPT.domains.entities.User;
import pw.ewen.WLPT.services.UserService;

import java.util.Base64;


/**
 * Created by wenliang on 17-2-13.
 * 用户认证模块
 */
@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationApi {

    //返回登录验证信息
    private static class AuthenticationInfo {

        private boolean result;
        private String info;
        private UserDTO user;


        public String getInfo() { return this.info;}
        public void setInfo(String info) {
            this.info = info;
        }

        public boolean getResult() { return this.result;}
        public void setResult(boolean result) {
            this.result = result;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }
    }

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    //客户端刷新服务器认证接口
    @RequestMapping(method = RequestMethod.PUT, value = "refresh", produces = "application/json")
    public boolean refresh() {
        return true;
    }

    //用户认证接口
//    @RequestMapping(method= RequestMethod.GET, produces="application/json")
//    public AuthenticationInfo authenticate(@RequestParam(value = "userid", defaultValue = "") String userid,
//                                @RequestParam(value = "token", defaultValue = "") String authToken){
//        AuthenticationInfo authInfo = new AuthenticationInfo();
//        UserDetails userDetails;
//        try{
//            userDetails = userDetailsService.loadUserByUsername(userid);
//            String authString = userDetails.getUsername() + ":" + userDetails.getPassword();
//            byte[] encodedBytes = Base64.getEncoder().encode(authString.getBytes());
//            String encodedAuthString = new String(encodedBytes);
//            if (authToken.equals(encodedAuthString)) {
//                authInfo.result = true;
//                authInfo.info = "认证成功";
//
//                User user = this.userService.findOne(userid);
//                UserDTO dto = UserDTO.convertFromUser(user);
//                authInfo.setUser(dto);
//            } else {
//                authInfo.result = false;
//                authInfo.info = "密码不正确";
//            }
//
//        }catch (UsernameNotFoundException exception){
//            authInfo.info = "用户名不存在";
//            authInfo.result = false;
//        }catch(Exception ex) {
//            authInfo.info = "登录失败";
//            authInfo.result = false;
//        }
//        return authInfo;
//    }
}
