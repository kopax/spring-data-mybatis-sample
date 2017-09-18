/*
 * Kopax Ltd Copyright (c) 2017.
 */

package com.domain.api.core.controllers;

import com.domain.api.core.domain.SiteService;
import com.domain.api.core.projections.SiteServiceDefaultProjection;
import com.domain.api.store.HttpPathStore;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasRole('USER')")
@RepositoryRestResource(collectionResourceRel = "siteServices", path = HttpPathStore.REPO_PATH_SITE_SERVICES, excerptProjection = SiteServiceDefaultProjection.class)
public interface SiteServiceRepository extends CrudRepository<SiteService, Long> {

	List<SiteService> findAll();

	@Query("getAll")
	List<SiteService> getAll();

}