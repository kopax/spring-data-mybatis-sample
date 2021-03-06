/*
 * Kopax Ltd Copyright (c) 2017.
 */

package com.domain.api.security.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dka on 3/17/17.
 */
@Configuration
public class AuthenticationHandler {

	@Autowired
	private ConsumerTokenServices consumerTokenServices;

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
				response.getWriter().append("\""+HttpStatus.UNAUTHORIZED.getReasonPhrase()+"\"");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		};
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
				response.getWriter().append("\""+HttpStatus.OK.getReasonPhrase()+"\"");
				response.setStatus(HttpStatus.OK.value());
			}
		};
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
				response.getWriter().append("\""+HttpStatus.UNAUTHORIZED.getReasonPhrase()+"\"");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		};

	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler () {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
				response.getWriter().append("\""+HttpStatus.FORBIDDEN.getReasonPhrase()+"\"");
				response.setStatus(HttpStatus.FORBIDDEN.value());
			}
		};

	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new LogoutSuccessHandler () {
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
				String authHeader = request.getHeader("Authorization");
				if (authHeader != null) {
					String tokenValue = authHeader.replace("Bearer", "").replace("bearer", "").trim();
					consumerTokenServices.revokeToken(tokenValue);
				}
				response.getWriter().append("\""+HttpStatus.OK.getReasonPhrase()+"\"");
				response.setStatus(HttpStatus.OK.value());
			}
		}
		;
	}
}
