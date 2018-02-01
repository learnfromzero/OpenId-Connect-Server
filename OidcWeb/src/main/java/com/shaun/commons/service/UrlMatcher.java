package com.shaun.commons.service;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/11.
 */
public interface UrlMatcher {
    Object compile(String paramString);
    boolean pathMatchesUrl(Object paramObject, String paramString);
    String getUniversalMatchPattern();
    boolean requiresLowerCaseUrl();
}
