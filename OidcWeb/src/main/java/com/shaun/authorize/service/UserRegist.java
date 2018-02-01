package com.shaun.authorize.service;

import com.shaun.authorize.entity.UserAccount;
import com.shaun.commons.service.IBaseDaoService;

import javax.servlet.http.HttpServletRequest;

public interface UserRegist extends IBaseDaoService{

    UserAccount getUserAccount(String userId);


    void regesitUserAccount(UserAccount userAccount, HttpServletRequest request);
}