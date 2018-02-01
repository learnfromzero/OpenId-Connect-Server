package com.shaun.oauth2.service;

import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @Author
 * @Description
 * @Date Created on 2017/11/7.
 */
public interface OAuthTokenService extends AuthorizationServerTokenServices,ResourceServerTokenServices {

}
