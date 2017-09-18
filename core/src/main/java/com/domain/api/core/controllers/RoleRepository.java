package com.domain.api.core.controllers;

import com.domain.api.core.domain.Role;
import com.domain.api.core.projections.RoleDefaultProjection;
import com.domain.api.store.HttpPathStore;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "roles", path = HttpPathStore.REPO_PATH_ROLES, excerptProjection = RoleDefaultProjection.class)
public interface RoleRepository extends MybatisRepository<Role, Long> {

	public Role findByName(String name);

	public List<Role> findById(@Param("role.id") Long id);



}