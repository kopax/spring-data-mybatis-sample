/*
 * Kopax Ltd Copyright (c) 2017.
 */

package com.domain.api.core.projections;

import com.domain.api.core.domain.Manager;
import com.domain.api.core.domain.Role;
import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;
import java.util.List;

/**
 * Created by dka on 1/13/17.
 */
@Projection(name = "managerDefault", types = { Manager.class })
public interface ManagerDefaultProjection {

	Long getId();

	String getUsername();

	Instant getCreatedDate();

	List<Role> getRoleList();

	Boolean getActive();

}
