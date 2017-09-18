package com.domain.api.services;

import com.domain.api.core.domain.OAuthClient;
import com.domain.api.core.controllers.OAuthClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public class OAuthClientDetailsService implements ClientDetailsService {

	@Autowired
	private final OAuthClientRepository oAuthClientRepository;

	@Autowired
	public OAuthClientDetailsService(OAuthClientRepository oAuthClientRepository) {
		this.oAuthClientRepository = oAuthClientRepository;
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		OAuthClient clientDetails = oAuthClientRepository.findByClientId(clientId);
		if (clientDetails != null) {
			return clientDetails;
		}
		throw new InvalidClientException(HttpStatus.NOT_FOUND.getReasonPhrase());
	}

}
