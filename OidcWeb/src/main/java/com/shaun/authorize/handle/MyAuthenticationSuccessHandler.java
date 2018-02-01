package com.shaun.authorize.handle;

import com.shaun.authorize.entity.MyUser;
import com.shaun.authorize.entity.UserAccount;
import com.shaun.authorize.service.UserRegist;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author
 * @Description
 * @Date Created on 2017/10/19.
 */
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public UserRegist getUserRegist() {
        return userRegist;
    }

    public void setUserRegist(UserRegist userRegist) {
        this.userRegist = userRegist;
    }

    private UserRegist userRegist;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        MyUser myUser = (MyUser) authentication.getPrincipal();
        String userId = myUser.getUserId();
        UserAccount userAccount = this.userRegist.getUserAccount(userId);
        this.userRegist.regesitUserAccount(userAccount,request);
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
