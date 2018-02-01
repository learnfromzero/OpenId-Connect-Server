package com.shaun.oauth2.controller;

import com.shaun.commons.util.HttpRequestor;
import com.shaun.oauth2.entity.OauthClientDetails;
import com.shaun.oauth2.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * @author
 * @Description
 * @Date Created on 2017/11/10.
 */
@Controller
public class AuthorizeController {

    @Autowired
    private OauthService oauthService;

    @RequestMapping("/oauth/confirm_access")
    public String confimAccess(AuthorizationRequest request, Principal pl){
        String clientId = request.getClientId();
        OauthClientDetails client = oauthService.loadOauthClientDetails(clientId);
        return "oauth/confirm_access";
    }


}
