/*******************************************************************************
 * Copyright 2017 The MIT Internet Trust Consortium
 *
 * Portions copyright 2011-2013 The MITRE Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.shaun.oauth2.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.*;
import com.nimbusds.jwt.JWTClaimsSet.Builder;
import com.shaun.authorize.entity.UserAccount;
import com.shaun.commons.service.UserInfoService;
import com.shaun.oauth2.encrypt.service.JWTSigningAndValidationService;
import com.shaun.oauth2.entity.OAuth2AccessTokenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConnectTokenEnhancer implements TokenEnhancer {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConnectTokenEnhancer.class);
	public String MAX_AGE = "max_age";

	@Autowired
	private JWTSigningAndValidationService jwtService;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private UserInfoService userInfoService;


	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		OAuth2AccessTokenEntity token = (OAuth2AccessTokenEntity) accessToken;
		OAuth2Request originalAuthRequest = authentication.getOAuth2Request();

		String clientId = originalAuthRequest.getClientId();
		ClientDetails client = clientDetailsService.loadClientByClientId(clientId);

		Builder builder = new Builder()
				.claim("azp", clientId)
				.issuer("http://localhost:8082/TrustedEP")
				.issueTime(new Date())
				.expirationTime(token.getExpiration())
				.subject(authentication.getName())
				.jwtID(UUID.randomUUID().toString()); // set a random NONCE in the middle of it

		String audience = (String) authentication.getOAuth2Request().getExtensions().get("aud");
		if (!Strings.isNullOrEmpty(audience)) {
			builder.audience(Lists.newArrayList(audience));
		}

		JWTClaimsSet claims = builder.build();

		JWSAlgorithm signingAlg = jwtService.getDefaultSigningAlgorithm();
		JWSHeader header = new JWSHeader(signingAlg, null, null, null, null, null, null, null, null, null,
				jwtService.getDefaultSignerKeyId(),
				null, null);
		SignedJWT signed = new SignedJWT(header, claims);

		jwtService.signJwt(signed);

		token.setJwt(signed);

		/**
		 * Authorization request scope MUST include "openid" in OIDC, but access token request
		 * may or may not include the scope parameter. As long as the AuthorizationRequest
		 * has the proper scope, we can consider this a valid OpenID Connect request. Otherwise,
		 * we consider it to be a vanilla OAuth2 request.
		 *
		 * Also, there must be a user authentication involved in the request for it to be considered
		 * OIDC and not OAuth, so we check for that as well.
		 */
		if (originalAuthRequest.getScope().contains("openid")
				) {

			String username = authentication.getName();
			UserAccount userInfo = userInfoService.getByUsername(username);

			if (userInfo != null) {

				JWT idToken = createIdToken(client,
						originalAuthRequest, claims.getIssueTime(),
						"",/*userInfo.getSub(),*/ token);

				// attach the id token to the parent access token
				try{

					token.setIdToken(idToken);
				}catch (Exception e){
					e.printStackTrace();
				}
			} else {
				// can't create an id token if we can't find the user
				logger.warn("Request for ID token when no user is present.");
			}
		}

		return token;
	}

	public JWT createIdToken(ClientDetails client, OAuth2Request request, Date issueTime, String sub, OAuth2AccessTokenEntity accessToken) {

		JWSAlgorithm signingAlg = jwtService.getDefaultSigningAlgorithm();

		JWT idToken = null;

		JWTClaimsSet.Builder idClaims = new JWTClaimsSet.Builder();

		// if the auth time claim was explicitly requested OR if the client always wants the auth time, put it in
		if (request.getExtensions().containsKey(MAX_AGE)
				|| (request.getExtensions().containsKey("idtoken")) // TODO: parse the ID Token claims (#473) -- for now assume it could be in there
				) {

			if (request.getExtensions().get("AUTH_TIMESTAMP") != null) {

				Long authTimestamp = Long.parseLong((String) request.getExtensions().get("AUTH_TIMESTAMP"));
				if (authTimestamp != null) {
					idClaims.claim("auth_time", authTimestamp / 1000L);
				}
			} else {
				// we couldn't find the timestamp!
				logger.warn("Unable to find authentication timestamp! There is likely something wrong with the configuration.");
			}
		}

		idClaims.issueTime(issueTime);


		idClaims.issuer("http://localhost:8082/TrustedEP");
		idClaims.subject(sub);
		idClaims.audience(Lists.newArrayList(client.getClientId()));
		idClaims.jwtID(UUID.randomUUID().toString()); // set a random NONCE in the middle of it


		idClaims.claim("kid", jwtService.getDefaultSignerKeyId());

		JWSHeader header = new JWSHeader(signingAlg, null, null, null, null, null, null, null, null, null,
				jwtService.getDefaultSignerKeyId(),
				null, null);

		idToken = new SignedJWT(header, idClaims.build());

		// sign it with the server's key
		jwtService.signJwt((SignedJWT) idToken);

		return idToken;
	}

	public JWTSigningAndValidationService getJwtService() {
		return jwtService;
	}

	public void setJwtService(JWTSigningAndValidationService jwtService) {
		this.jwtService = jwtService;
	}

	public ClientDetailsService getClientService() {
		return clientDetailsService;
	}

	public void setClientService(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}


}