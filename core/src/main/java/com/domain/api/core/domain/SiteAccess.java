/*
 * Kopax Ltd Copyright (c) 2017.
 */

package com.domain.api.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mybatis.annotations.Column;

import javax.validation.constraints.NotNull;

/**
 * Created by dka on 1/13/17.
 */
public abstract class SiteAccess extends LongId {

	@Column(name = "NAME")
	@NotNull
	private String name;

	protected SiteAccess() {
	}

	public SiteAccess(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
