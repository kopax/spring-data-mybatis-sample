package com.domain.api.config;

import com.domain.api.i18n.MultiModuleReloadableResourceBundleMessageSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dka on 4/9/17.
 */
@Configuration
@EnableConfigurationProperties(MessageProperties.class)
public class I18nConfiguration {

	@Bean
	public MessageSource messageSource(MessageProperties properties) {
		MultiModuleReloadableResourceBundleMessageSource messageSource = new MultiModuleReloadableResourceBundleMessageSource();
		messageSource.setBasenames(properties.getBasenames());
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}


}
