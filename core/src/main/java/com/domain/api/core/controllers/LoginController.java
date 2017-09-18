/*
 * Kopax Ltd Copyright (c) 2016.
 */

package com.domain.api.core.controllers;

import com.domain.api.config.Oauth2Properties;
import com.domain.api.core.domain.OAuthClient;
import com.domain.api.store.HttpPathStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class LoginController {

	private Oauth2Properties oauth2Properties;

	@Autowired
	private void setOauth2Properties(Oauth2Properties oauth2Properties) {
		this.oauth2Properties = oauth2Properties;
	}

	@Autowired
	private OAuthClientRepository oAuthClientRepository;

	/**
	 * The client SHOULD verify if the key is correct
	 *
	 * @param clientId
	 * @param locale
	 * @return the client_id + "_" + resource_id;
	 */
	@RequestMapping(value = HttpPathStore.LOGIN_OAUTH_CLIENT_CB, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public
	@ResponseBody
	ResponseEntity cb(
			@PathVariable(value = HttpPathStore.PARAM_CLIENT_VALUE) final String clientId,
			final Locale locale) throws OAuth2AccessDeniedException {
		try {
			OAuthClient client = oAuthClientRepository.findByClientId(clientId);
			return ResponseEntity.ok("\"" + client.getClientId() + "_" + oauth2Properties.getResource().getPublickey() + "\"");
		} catch (Exception e) {
			throw new OAuth2AccessDeniedException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
		}
	}

    @RequestMapping(value = HttpPathStore.LOGIN, method = RequestMethod.GET)
    public ResponseEntity nullLogin() {
	    return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
