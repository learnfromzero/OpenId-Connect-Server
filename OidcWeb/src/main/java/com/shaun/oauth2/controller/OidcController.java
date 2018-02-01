package com.shaun.oauth2.controller;

import com.alibaba.fastjson.JSON;
import com.shaun.oauth2.entity.OauthClientDetails;
import com.shaun.oauth2.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author
 * @Description
 * @Date Created on 2017/11/28.
 */
@Controller
@RequestMapping("/oidc")
public class OidcController {

    @Autowired
    private OauthService oauthService;

    @RequestMapping("/clientmng.do")
    public String success(){
        return "oidc/client_mng";
    }

    @RequestMapping("/clientDetails.do")
    @ResponseBody
    public String queryAllClients(){
        List<OauthClientDetails> clientDetailsList = oauthService.loadAllOauthClientDetails();
        return JSON.toJSONString(clientDetailsList);
    }

}
