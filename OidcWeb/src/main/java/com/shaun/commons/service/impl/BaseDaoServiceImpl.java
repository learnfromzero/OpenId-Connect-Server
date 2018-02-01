package com.shaun.commons.service.impl;

import com.shaun.commons.dao.IDao;
import com.shaun.commons.service.BaseService;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/6.
 */
public class BaseDaoServiceImpl extends BaseService {
    private IDao appDao;  //应用库dao;

    public IDao getAppDao() {
        return appDao;
    }

    public void setAppDao(IDao appDao) {
        this.appDao = appDao;
    }
}
