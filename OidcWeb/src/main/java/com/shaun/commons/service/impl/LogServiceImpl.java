package com.shaun.commons.service.impl;

import com.shaun.commons.entity.LogEntity;
import com.shaun.commons.service.LogService;

/**
 * @Author
 * @Description
 * @Date Created on 2017/11/21.
 */
public class LogServiceImpl extends BaseDaoServiceImpl implements LogService {

    @Override
    public void saveLog(LogEntity log) {
        getAppDao().insert("log.saveLog",log);
    }
}
