package com.shaun.authorize.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/8.
 */
public class AppRole implements GrantedAuthority{
    public static String ROLE_USER = "ROLE_USER";
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
