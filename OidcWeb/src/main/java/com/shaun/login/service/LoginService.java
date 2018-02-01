package com.shaun.login.service;

import com.shaun.commons.service.IBaseDaoService;

import java.util.Map;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/6.
 */
public interface LoginService extends IBaseDaoService{

    Map queryByUserName(String userName);

    Map queryBindUser(Map dtoMap);

    int saveBind(Map dtoMap);
}
