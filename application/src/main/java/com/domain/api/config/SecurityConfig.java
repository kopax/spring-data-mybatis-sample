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
package com.domain.api.config;

import com.domain.api.store.HttpPathStore;
import com.domain.api.interfaceconfigs.ManagerDetailsService;
import com.domain.api.security.addons.CorsCookieCsrfTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private CookieProperties cookieProperties;

	@Autowired
	private ManagerDetailsService managerDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.managerDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.requestMatcher(
				new OrRequestMatcher(
					new AntPathRequestMatcher(HttpPathStore.LOGIN),
					new AntPathRequestMatcher(HttpPathStore.LOGOUT),
					new AntPathRequestMatcher(HttpPathStore.PING),
					new AntPathRequestMatcher(HttpPathStore.OAUTH_AUTHORIZE_PATH),
					new AntPathRequestMatcher(HttpPathStore.OAUTH_REDIRECT_PATTERN),
					new AntPathRequestMatcher(HttpPathStore.REST_PATH)
				)
			)
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.and()
			.csrf()
				.csrfTokenRepository(corsCookieCsrfTokenRepository())
				.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, HttpPathStore.PING).permitAll()
				.anyRequest().hasRole("USER")
			.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint)
			.and()
				.formLogin()
				.loginProcessingUrl(HttpPathStore.LOGIN)
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.permitAll()
			.and()
				.logout()
				.logoutUrl(HttpPathStore.LOGOUT)
				.logoutSuccessUrl(HttpPathStore.LOGIN_FROM_LOGOUT)
				.logoutSuccessHandler(logoutSuccessHandler)
				.permitAll();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		if (null != cookieProperties.getName()) { serializer.setCookieName(cookieProperties.getName()); }
		if (null != cookieProperties.getPath()) { serializer.setCookiePath(cookieProperties.getPath()); }
		if (null != cookieProperties.getHttpOnly()) { serializer.setUseHttpOnlyCookie(cookieProperties.getHttpOnly()); }
		if (null != cookieProperties.getMaxAge()) { serializer.setCookieMaxAge(cookieProperties.getMaxAge()); }
		if (null != cookieProperties.getSecure()) { serializer.setUseSecureCookie(cookieProperties.getSecure()); }
		if (null != cookieProperties.getDomain()) { serializer.setDomainName(cookieProperties.getDomain()); }
		return serializer;
	}

	@Bean
	public CorsCookieCsrfTokenRepository corsCookieCsrfTokenRepository(){
		CorsCookieCsrfTokenRepository repository = new CorsCookieCsrfTokenRepository();
		repository.setCookieHttpOnly(false);
		repository.setHeaderName("X-XSRF-TOKEN");
		repository.setCookiePath(cookieProperties.getPath());
		repository.setCookieDomain(cookieProperties.getDomain());
		repository.setCookieName("XSRF-TOKEN");
		return repository;
	}
}