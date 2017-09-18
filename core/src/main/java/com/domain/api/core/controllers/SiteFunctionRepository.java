/*
 * Kopax Ltd Copyright (c) 2017.
 */

package com.domain.api.core.controllers;

import com.domain.api.core.projections.SiteFunctionDefaultProjection;
import com.domain.api.core.domain.SiteFunction;
import com.domain.api.store.HttpPathStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasRole('USER')")
@RepositoryRestResource(collectionResourceRel = "siteFunctions", path = HttpPathStore.REPO_PATH_SITE_FUNCTIONS, excerptProjection = SiteFunctionDefaultProjection.class)
public interface SiteFunctionRepository extends CrudRepository<SiteFunction, Long> {

//	List<SiteFunction> findBySiteService(SiteService siteService);

	List<SiteFunction> findBySiteServiceId(@Param("id") Long id);

}