package com.shaun.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luotao
 * @Description
 * @Date Created on 2017/9/7.
 */
public class BaseController {
    public Object getService(String serviceName){
        WebApplicationContext wc = ContextLoader.getCurrentWebApplicationContext();
        return wc.getBean(serviceName);
    }

    @Autowired
    protected HttpServletRequest request;
}
