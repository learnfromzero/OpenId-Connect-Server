package com.shaun.authorize.service.impl;

import com.alibaba.fastjson.JSON;
import com.shaun.authorize.entity.UserAccount;
import com.shaun.authorize.service.UserRegist;
import com.shaun.commons.service.impl.BaseDaoServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @Description
 * @Date Created on 2017/10/19.
 */
public class UserRegistImpl extends BaseDaoServiceImpl implements UserRegist {
    @Override
    public UserAccount getUserAccount(String userId) {
        Map map = new HashMap();
        map.put("userId",userId);
        return (UserAccount) getAppDao().queryForObject("login.queryUserAccount", map);
    }

    @Override
    public void regesitUserAccount(UserAccount userAccount, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userAccountInfo", userAccount);
        String roleId = userAccount.getRoleId();
        List<Map> list = new ArrayList<Map>();
        if (roleId.indexOf(",") != -1) {
            String[] roles = roleId.split(",");
            for (String id : roles) {
                roleId = id;
                list = getAppDao().queryForList("login.queryMenuByRoleId", roleId);
            }
        } else {
            list = getAppDao().queryForList("login.queryMenuByRoleId", roleId);
        }
        session.setAttribute("_USER_MENUS_", JSON.toJSONString(list));
    }
}
