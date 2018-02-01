package com.shaun.oauth2.service;



import com.shaun.oauth2.entity.OauthClientDetails;

import java.util.List;

/**
 * 处理 OAuth 相关业务的 Repository
 *
 * @author Shaun_luo
 */
public interface OauthRepository {

    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails();

    List<OauthClientDetails> findOauthClientDetailsByUser(String userName);

    void updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OauthClientDetails clientDetails);
}