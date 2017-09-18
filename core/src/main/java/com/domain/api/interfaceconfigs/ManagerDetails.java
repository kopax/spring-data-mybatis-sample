package com.domain.api.interfaceconfigs;


/**
 * Created by dka on 4/10/17.
 */

import java.util.Set;

/**
 * Generic version of the Box class.
 * @param <T> the type of the value being boxed
 */
public interface ManagerDetails<T> {

	String getUsername();

	void setUsername(String username);

	Set<T> getRoleList();

	void setRoleList(Set<T> roleList);

	String getPassword();

	void setPassword(String password);

}
