/*
 * Kopax Ltd Copyright (c) 2017.
 */

package com.domain.api.core.projections;

import com.domain.api.core.domain.SiteService;
import com.domain.api.core.domain.SiteContent;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by dka on 1/13/17.
 */
@Projection(name = "siteFunctionList", types = { SiteService.class })
public interface SiteFunctionDefaultProjection {

	String getName();

	SiteService getSiteService();

	List<SiteContent> getSiteContentList();

}
