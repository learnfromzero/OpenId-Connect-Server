package com.shaun.commons.service;

import com.shaun.commons.dao.IDao;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/6.
 */
public class BaseService {

    protected IDao dao;

    public BaseService() {
    }

    public IDao getDao() {
        return this.dao;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

}
