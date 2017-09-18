/*
 * Kopax Ltd Copyright (c) 2017.
 */

package com.domain.api.core.projections;

import com.domain.api.core.domain.OAuthClient;
import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;

@Projection(name = "OAuthClientDefault", types = { OAuthClient.class })
public interface OAuthClientDefaultProjection {

	String getClientId();

	Instant getCreatedDate();

	Boolean getActive();

}
