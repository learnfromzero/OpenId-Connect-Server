package com.shaun.login.service.impl;

import com.shaun.commons.dao.IDao;
import com.shaun.commons.service.impl.BaseDaoServiceImpl;
import com.shaun.login.service.LoginService;

import java.util.Map;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/6.
 */
public class LoginServiceImpl extends BaseDaoServiceImpl implements LoginService {
    @Override
    public Map queryByUserName(String userName){
        IDao iDao = super.getAppDao();
        return (Map)iDao.queryForObject("login.queryByUserName",userName);
    }

    @Override
    public Map queryBindUser(Map dtoMap) {
        return (Map)super.getAppDao().queryForObject("login.queryBindUser",dtoMap);
    }

    @Override
    public int saveBind(Map dtoMap) {
        return super.getAppDao().update("login.saveBind",dtoMap);
    }
}
