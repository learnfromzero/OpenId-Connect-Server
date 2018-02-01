package com.shaun.oauth2.service.impl;

import com.shaun.commons.service.impl.BaseDaoServiceImpl;
import com.shaun.oauth2.dto.OauthClientDetailsDto;
import com.shaun.oauth2.entity.OauthClientDetails;
import com.shaun.oauth2.service.OauthRepository;
import com.shaun.oauth2.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OAuth 业务处理服务对象, 事务拦截也加在这一层
 */
public class OauthServiceImpl extends BaseDaoServiceImpl implements OauthService{

    @Autowired
    private OauthRepository oauthRepository;

    @Override
    public OauthClientDetails loadOauthClientDetails(String clientId) {
        Map map = new HashMap();
        map.put("clientId",clientId);
        return (OauthClientDetails)getAppDao().queryForObject("oauth.loadOauthClientDetails",map);
//        return oauthRepository.findOauthClientDetails(clientId);
    }

    @Override
    public List<OauthClientDetails> loadAllOauthClientDetails() {
        List <OauthClientDetails> list = getAppDao().queryForList("oauth.loadAllOauthClientDetails");
        return list;
    }

    @Override
    public List<OauthClientDetailsDto> loadOauthClientDetailsDtosByUser(String userName) {
        List<OauthClientDetails> clientDetailses = oauthRepository.findOauthClientDetailsByUser(userName);
        return OauthClientDetailsDto.toDtos(clientDetailses);
    }

    @Override
    public void archiveOauthClientDetails(String clientId) {
        oauthRepository.updateOauthClientDetailsArchive(clientId, true);
    }

    @Override
    public OauthClientDetailsDto loadOauthClientDetailsDto(String clientId) {
        final OauthClientDetails oauthClientDetails = oauthRepository.findOauthClientDetails(clientId);
        return oauthClientDetails != null ? new OauthClientDetailsDto(oauthClientDetails) : null;
    }

    @Override
    public void registerClientDetails(OauthClientDetailsDto formDto) {
        OauthClientDetails clientDetails = formDto.createDomain();
        oauthRepository.saveOauthClientDetails(clientDetails);
    }
}