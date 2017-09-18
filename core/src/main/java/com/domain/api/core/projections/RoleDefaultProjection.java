/*
 * Kopax Ltd Copyright (c) 2017.
 */

package com.domain.api.core.projections;

import com.domain.api.core.domain.Role;
import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;

@Projection(name = "roleDefault", types = { Role.class })
public interface RoleDefaultProjection {

	Long getId();

	String getName();

	Instant getCreatedDate();

}
