package com.domain.api.config;

import com.domain.api.services.ManagerDetailsServiceImpl;
import com.domain.api.services.OAuthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.sql.SQLException;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
@Import({ CoreDbConfig.class, ManagerDetailsServiceImpl.class, OAuthClientDetailsService.class })
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private CoreDbConfig coreDbConfig;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	private ManagerDetailsServiceImpl managerDetailsService;

	@Autowired
	private OAuthClientDetailsService oAuthClientDetailsService;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Oauth2Properties oauth2Properties;

	private TokenEnhancer tokenEnhancer;

	@Autowired
	public void setTokenEnhancer(TokenEnhancer tokenEnhancer) {
		this.tokenEnhancer = tokenEnhancer;
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
				.passwordEncoder(passwordEncoder)
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(oAuthClientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		endpoints
				.tokenStore(tokenStore())
				.approvalStoreDisabled()
				.authorizationCodeServices(authorizationCodeServices())
				.tokenEnhancer(tokenEnhancerChain)
				.reuseRefreshTokens(false)
				.authenticationManager(authenticationManager)
				.userDetailsService(managerDetailsService);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		return converter;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return this.tokenEnhancer;
	}

	@Bean
	public TokenStore tokenStore() throws SQLException {
		return new JdbcTokenStore(coreDbConfig.dataSource());
	}

	@Bean
	protected AuthorizationCodeServices authorizationCodeServices() throws SQLException {
		return new JdbcAuthorizationCodeServices(coreDbConfig.dataSource());
	}

}
