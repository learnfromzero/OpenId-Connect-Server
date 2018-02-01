package com.shaun.authorize.util;


import org.springframework.security.core.AuthenticationException;

/**
 * @Author
 * @Description
 * @Date Created on 2017/11/1.
 */
public class MyAuthenticationException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public MyAuthenticationException(String msg) {
        super(msg);
    }
}
