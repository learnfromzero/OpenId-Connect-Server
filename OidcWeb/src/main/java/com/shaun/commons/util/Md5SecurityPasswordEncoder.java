package com.shaun.commons.util;


import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/11.
 */
public class Md5SecurityPasswordEncoder implements PasswordEncoder{
    @Override
    public String encodePassword(String rawPass, Object salt) {
        if ("userNotFoundPassword".equals(rawPass)) {
            return "";
        } else {
            return Md5EncodeUtil.md5Encode(rawPass);
        }
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        return encPass.equals(this.encodePassword(rawPass, salt));
    }
}
