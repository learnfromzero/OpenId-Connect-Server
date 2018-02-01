package com.shaun.commons.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/8.
 */
public class Md5EncodeUtil {

    public static Md5EncodeUtil instance;
    private static Log log = LogFactory.getLog(Md5EncodeUtil.class);

    static {
        instance = new Md5EncodeUtil();
    }

    public static Md5EncodeUtil getInstance(){
        return instance;
    }

    public static String md5Encode(String str){
        String beforeEncodeStr = str+"-a1b2";//偏移码（salt）-a1b2
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            String encodedStr = base64Encoder.encode(md5.digest(beforeEncodeStr.getBytes("UTF-8")));
            return encodedStr;
        }catch (NoSuchAlgorithmException e){
            log.error(e);
            return null;
        }catch (UnsupportedEncodingException e){
            log.error(e);
            return null;
        }

    }
}
