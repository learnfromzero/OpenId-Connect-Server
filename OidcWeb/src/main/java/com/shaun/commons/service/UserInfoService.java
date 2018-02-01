package com.shaun.commons.service;

import com.shaun.authorize.entity.UserAccount;

/**
 * @Author
 * @Description
 * @Date Created on 2017/11/8.
 */
public interface UserInfoService extends IBaseDaoService {
    UserAccount getByUsername(String username);
}
