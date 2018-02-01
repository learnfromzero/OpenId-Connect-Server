package com.shaun.commons.service;


import com.shaun.commons.entity.LogEntity;

/**
 * @Author
 * @Description
 * @Date Created on 2017/11/21.
 */
public interface LogService extends IBaseDaoService {
    void saveLog(LogEntity log);
}
