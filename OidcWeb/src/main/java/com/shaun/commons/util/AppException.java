package com.shaun.commons.util;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/6.
 */
public class AppException extends Exception {
    private static final long serivalVersionId = 1L;

    public AppException(){
        super();
    }
    public AppException(String msg){
        super(msg);
    }
}
