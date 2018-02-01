package com.shaun.oauth2.service.impl;

import com.shaun.oauth2.entity.OauthClientDetails;
import com.shaun.oauth2.entity.OauthClientDetailsRowMapper;
import com.shaun.oauth2.service.OauthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shaun.oauth2.util.CacheConstants.CLIENT_DETAILS_CACHE;


/**
 * 2015/11/16
 *
 * @author Shaun_luo
 */
@Repository("oauthRepositoryJdbc")
public class OauthRepositoryJdbc implements OauthRepository {


    private static OauthClientDetailsRowMapper oauthClientDetailsRowMapper = new OauthClientDetailsRowMapper();


    @Autowired
    private JdbcTemplate jdbcTemplateMysql;


    @Override
    public OauthClientDetails findOauthClientDetails(String clientId) {
        final String sql = " select * from oauth_client_details where  client_id = ? ";
        final List<OauthClientDetails> list = this.jdbcTemplateMysql.query(sql, new Object[]{clientId}, oauthClientDetailsRowMapper);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<OauthClientDetails> findAllOauthClientDetails() {
        final String sql = " select * from oauth_client_details order by create_time desc ";
        return this.jdbcTemplateMysql.query(sql, oauthClientDetailsRowMapper);
    }

    @Override
    public List<OauthClientDetails> findOauthClientDetailsByUser(String create_user) {
        final String sql = "select * from oauth_client_details where create_user = ? order by create_time desc";
        return this.jdbcTemplateMysql.query(sql,new Object[]{create_user},oauthClientDetailsRowMapper);
    }

    @Override
    @CacheEvict(value = CLIENT_DETAILS_CACHE, key = "#clientId")
    public void updateOauthClientDetailsArchive(String clientId, boolean archive) {
        final String sql = " update oauth_client_details set archived = ? where client_id = ? ";
        this.jdbcTemplateMysql.update(sql, archive, clientId);
    }

    @Override
    public void saveOauthClientDetails(final OauthClientDetails clientDetails) {
        final String sql = " insert into oauth_client_details(client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri," +
                " authorities,access_token_validity,refresh_token_validity,additional_information,trusted,autoapprove) values (?,?,?,?,?,?,?,?,?,?,?,?)";

        this.jdbcTemplateMysql.update(sql, ps -> {
            ps.setString(1, clientDetails.clientId());
            ps.setString(2, clientDetails.resourceIds());

            ps.setString(3, clientDetails.clientSecret());
            ps.setString(4, clientDetails.scope());

            ps.setString(5, clientDetails.authorizedGrantTypes());
            ps.setString(6, clientDetails.webServerRedirectUri());

            ps.setString(7, clientDetails.authorities());
            ps.setObject(8, clientDetails.accessTokenValidity());

            ps.setObject(9, clientDetails.refreshTokenValidity());
            ps.setString(10, clientDetails.additionalInformation());

            ps.setBoolean(11, clientDetails.trusted());
            ps.setString(12, clientDetails.autoApprove());

        });
    }
}
