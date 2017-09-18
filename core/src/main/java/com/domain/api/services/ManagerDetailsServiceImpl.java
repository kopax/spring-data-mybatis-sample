/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.domain.api.services;


import com.domain.api.core.controllers.ManagerRepository;
import com.domain.api.core.domain.Manager;
import com.domain.api.interfaceconfigs.ManagerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ManagerDetailsServiceImpl implements ManagerDetailsService {

	private final ManagerRepository managerRepository;

	@Autowired
	public ManagerDetailsServiceImpl(ManagerRepository managerRepository) {
		this.managerRepository = managerRepository;
	}

	@Override
	public Manager loadUserByUsername(String username) throws UsernameNotFoundException {
		Manager manager = this.managerRepository.findByUsername(username);
		if( null == manager ){
			throw new UsernameNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase());
		}
		return manager;
	}
}
