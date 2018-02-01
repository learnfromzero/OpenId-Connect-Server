package com.shaun.authorize.filter;

import com.shaun.authorize.entity.MyUser;
import com.shaun.authorize.util.MyAuthenticationException;
import com.shaun.commons.util.AppException;
import com.shaun.commons.util.SystemConfigUtil;
import nl.captcha.Captcha;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/11.
 */
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String useCheckCode = SystemConfigUtil.getProperties("useCheckCode","false");
        String isTrue = "true";
        if(isTrue.equals(useCheckCode)){
            String checkCode = request.getParameter("checkCode");
            Captcha captcha = (Captcha) request.getSession().getAttribute("simpleCaptcha");
            String answer = captcha.getAnswer();
            if(!checkCode.equals(answer)){
                throw new MyAuthenticationException("CheckCode is Invalid");
            }
        }
        Authentication authentication = super.attemptAuthentication(request, response);
        return authentication;
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return super.obtainPassword(request);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return super.obtainUsername(request);
    }

    @Override
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        super.setDetails(request, authRequest);
    }
}
