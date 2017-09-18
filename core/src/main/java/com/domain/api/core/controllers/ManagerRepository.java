package com.domain.api.core.controllers;

import com.domain.api.core.domain.Manager;
import com.domain.api.core.projections.ManagerDefaultProjection;
import com.domain.api.store.HttpPathStore;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

//@PreAuthorize("hasRole('MANAGER')")
@RepositoryRestResource(collectionResourceRel = "managers", path = HttpPathStore.REPO_PATH_MANAGERS, excerptProjection = ManagerDefaultProjection.class)
public interface ManagerRepository extends MybatisRepository<Manager, Long> {

//	@PreAuthorize("permitAll")
	public Manager findByUsername(String login);

}