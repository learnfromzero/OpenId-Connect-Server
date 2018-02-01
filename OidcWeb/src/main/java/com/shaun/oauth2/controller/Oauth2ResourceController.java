package com.shaun.oauth2.controller;

import com.shaun.authorize.entity.UserAccount;
import com.shaun.commons.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author
 * @Description
 * @Date Created on 2017/11/13.
 */
@Controller
@RequestMapping("/user")
public class Oauth2ResourceController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     *
     * @param
     * @return
     * and #oauth2.hasScope('openid')
     */
    @RequestMapping("userinfo.do")
    @ResponseBody
    public UserAccount userInfo(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userInfo = new UserAccount();
        if (authentication instanceof OAuth2Authentication){
            String username = authentication.getName();
            userInfo = userInfoService.getByUsername(username);
        }else{
            throw new AuthenticationCredentialsNotFoundException("授权失败");
        }
        return userInfo;
    }

}
