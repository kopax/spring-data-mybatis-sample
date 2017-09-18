package com.domain.api.mybatis.config;

import com.domain.api.core.domain.Manager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by dka on 4/9/17.
 */

@Configuration
public class AuditorAwareConfiguration {

	@Bean
	public AuditorAware<Long> auditorAware() {
		return new AuditorAware<Long>() {
			@Override
			public Long getCurrentAuditor() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (authentication == null || !authentication.isAuthenticated()) {
					return null;
				}

				Manager manager = null;
				Object principal = authentication.getPrincipal();

				if (principal.getClass().equals(Manager.class)) {
					manager = (Manager) principal;
				}

				if (null == manager) {
					return null;
				}

				return manager.getId();
			}
		};
	}
}
