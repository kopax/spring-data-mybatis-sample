package com.domain.api.core.controllers;

import com.domain.api.core.domain.OAuthClient;
import com.domain.api.core.projections.OAuthClientDefaultProjection;
import com.domain.api.store.HttpPathStore;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(collectionResourceRel = "oAuthClients", path = HttpPathStore.REPO_PATH_OAUTH_CLIENT, excerptProjection = OAuthClientDefaultProjection.class)
public interface OAuthClientRepository extends MybatisRepository<OAuthClient, Long> {

	public OAuthClient findByClientId(String clientId);

}
