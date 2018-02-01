package com.shaun.oauth2.entity;

import com.nimbusds.jwt.JWT;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.io.Serializable;
import java.util.*;

/**
 * @Author
 * @Description
 * @Date Created on 2017/11/8.
 */
public class OAuth2AccessTokenEntity implements Serializable,OAuth2AccessToken {
    private static final long serialVersionUID = 914967629530462926L;
    private String value;
    private Date expiration;
    private String tokenType;
    private OAuth2RefreshToken refreshToken;
    private Set<String> scope;
    private Map<String, Object> additionalInformation;

    private JWT jwtValue;

    public static final String ID_TOKEN_FIELD_NAME = "id_token";

    public OAuth2AccessTokenEntity(String value) {
        this.tokenType = "Bearer".toLowerCase();
        this.additionalInformation = new HashMap<>();
        this.value = value;
    }

    private OAuth2AccessTokenEntity() {
        this((String)null);
    }

    public OAuth2AccessTokenEntity(OAuth2AccessToken accessToken) {
        this(accessToken.getValue());
        this.setRefreshToken(accessToken.getRefreshToken());
        this.setExpiration(accessToken.getExpiration());
        this.setScope(accessToken.getScope());
        this.setTokenType(accessToken.getTokenType());
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public int getExpiresIn() {
        return this.expiration != null ? (int)((this.expiration.getTime() - System.currentTimeMillis())/1000L)  : 0;
    }

    protected void setExpiresIn(int delta) {
        this.setExpiration(new Date(System.currentTimeMillis() + (long)delta));
    }
    @Override
    public Date getExpiration() {
        return this.expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
    @Override
    public boolean isExpired() {
        return this.expiration != null && this.expiration.before(new Date());
    }
    @Override
    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(OAuth2RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }
    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }
    @Override
    public boolean equals(Object obj) {
        return obj != null && this.toString().equals(obj.toString());
    }
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    @Override
    public String toString() {
        return String.valueOf(this.getValue());
    }

    public static OAuth2AccessToken valueOf(Map<String, String> tokenParams) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken((String)tokenParams.get("access_token"));
        if (tokenParams.containsKey("expires_in")) {
            long expiration = 0L;

            try {
                expiration = Long.parseLong(String.valueOf(tokenParams.get("expires_in")));
            } catch (NumberFormatException var5) {
                ;
            }

            token.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L));
        }

        if (tokenParams.containsKey("refresh_token")) {
            String refresh = (String)tokenParams.get("refresh_token");
            DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken(refresh);
            token.setRefreshToken(refreshToken);
        }

        if (tokenParams.containsKey("scope")) {
            Set<String> scope = new TreeSet();
            StringTokenizer tokenizer = new StringTokenizer((String)tokenParams.get("scope"), " ,");

            while(tokenizer.hasMoreTokens()) {
                scope.add(tokenizer.nextToken());
            }

            token.setScope(scope);
        }

        if (tokenParams.containsKey("token_type")) {
            token.setTokenType((String)tokenParams.get("token_type"));
        }

        return token;
    }
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

    /*public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = new HashMap<>(additionalInformation);
    }*/


    public JWT getJwt() {
        return jwtValue;
    }

    public void setJwt(JWT jwt) {
        this.jwtValue = jwt;
    }

    public void setIdToken(JWT idToken) {
        if (idToken != null) {
            this.additionalInformation.put(ID_TOKEN_FIELD_NAME, idToken.serialize());
        }
    }
}
