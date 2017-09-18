package com.domain.api.interfaceconfigs;


import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Created by dka on 4/10/17.
 */
public interface OAuthClientDetails<M, R> {

	public M getManager();

	public void setManager(M manager);

	public Set<R> getRoleList();

	public void setRoleList(Set<R> roleList);

	public Integer getVersion();

	public void setVersion(Integer version);

	public Instant getCreatedDate();

	public void setCreatedDate(Instant createdDate);

	public Instant getLastModifiedDate();

	public void setLastModifiedDate(Instant lastModifiedDate);

	public Long getCreatedById();

	public void setCreatedById(Long createdById);

	public Long getLastModifiedById();

	public void setLastModifiedById(Long lastModifiedById);

	public Boolean getActive();

	public void setActive(Boolean active);

}
