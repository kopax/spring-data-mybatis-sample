/*
 *
 *   Copyright 2016 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.domain.api.core.domain;

import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.JdbcType;

import static org.apache.ibatis.type.JdbcType.VARCHAR;


/**
 * Sample domain class representing roles. Mapped with XML.
 */
@Entity(table = "ROLE")
public class Role extends VersionId {

	private static final String PREFIX = "ROLE_";

	@JdbcType(VARCHAR)
	@Column(name = "NAME")
	private String name;


	/**
	 * Creates a new instance of {@code Role}.
	 */
	public Role() {
	}

	/**
	 * Creates a new preconfigured {@code Role}.
	 *
	 * @param name
	 */
	public Role(final String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Role role = (Role) o;

		return id != null ? id.equals(role.id) : role.id == null;

	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
	@Override
	public String toString() {

		return PREFIX + name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
