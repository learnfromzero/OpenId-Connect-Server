package com.shaun.oauth2.service;


import com.shaun.oauth2.dto.OauthClientDetailsDto;
import com.shaun.oauth2.entity.OauthClientDetails;

import java.util.List;

/**
 * @author Shaun_luo
 */

public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadOauthClientDetailsDtosByUser(String userName);

    List<OauthClientDetails> loadAllOauthClientDetails();

    void archiveOauthClientDetails(String clientId);

    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

    void registerClientDetails(OauthClientDetailsDto formDto);
}