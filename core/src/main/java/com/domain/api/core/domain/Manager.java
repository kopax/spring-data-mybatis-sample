package com.domain.api.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.domain.api.interfaceconfigs.ManagerDetails;
import org.springframework.data.mybatis.annotations.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.apache.ibatis.type.JdbcType.VARCHAR;

@Entity(table = "MANAGER")
public class Manager extends VersionId implements ManagerDetails<Role>, UserDetails, CredentialsContainer {

	@JdbcType(VARCHAR)
	@Column(name = "USERNAME")
	private String username;

	@JsonIgnore
	@JdbcType(VARCHAR)
	@Column(name = "PASSWORD")
	private String password;

	@JdbcType(VARCHAR)
	@ManyToMany
	@JoinTable(
		name = "LINK_MANAGER_ROLE",
		joinColumns = @JoinColumn(name = "\"MANAGER_ID\"", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "\"ROLE_ID\"", referencedColumnName = "ID")
	)
	private Set<Role> roleList;

	protected Manager() {
	}

	public Manager(String username, String password, Set<Role> roleList) {
		this.username = username;
		this.password = password;
		this.roleList = roleList;
	}

	public Manager(String username) {
		this.username = username;
	}

	public Set<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isEnabled() {
		return getActive();
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roleList = getRoleList();
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roleList) {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
		return authorities;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public void eraseCredentials() {
		setPassword(null);
	}
}
