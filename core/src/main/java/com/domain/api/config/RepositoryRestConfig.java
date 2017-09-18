/*
 * Kopax Ltd Copyright (c) 2017.
 */

package com.domain.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.domain.api.store.HttpPathStore;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {

	/* (non-Javadoc)
	 * @see org.springframework.user.rest.webmvc.config.RepositoryRestMvcConfiguration#configureJacksonObjectMapper(com.fasterxml.jackson.databind.ObjectMapper)
	 */

	@Override
	public void configureJacksonObjectMapper(ObjectMapper mapper) {
		super.configureJacksonObjectMapper(mapper);
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.setBasePath(HttpPathStore.REST_PATH);
		config.setReturnBodyOnCreate(true);
		config.setReturnBodyOnUpdate(true);
	}

}
