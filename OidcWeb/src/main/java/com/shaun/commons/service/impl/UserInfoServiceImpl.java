package com.shaun.commons.service.impl;

import com.shaun.authorize.entity.UserAccount;
import com.shaun.commons.service.UserInfoService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Description
 * @Date Created on 2017/11/8.
 */
public class UserInfoServiceImpl extends BaseDaoServiceImpl implements UserInfoService {
    @Override
    public UserAccount getByUsername(String username) {
        Map map = new HashMap();
        map.put("username",username);
        return (UserAccount) getAppDao().queryForObject("login.queryUserAccount",map);
    }
}
