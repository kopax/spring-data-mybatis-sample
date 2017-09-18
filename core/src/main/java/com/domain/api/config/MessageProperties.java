package com.domain.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by dka on 4/10/17.
 */
@ConfigurationProperties("api.messages")
public class MessageProperties {

	private String[] basenames;

	public String[] getBasenames() {
		return basenames;
	}

	public void setBasenames(String[] basenames) {
		this.basenames = basenames;
	}
}
