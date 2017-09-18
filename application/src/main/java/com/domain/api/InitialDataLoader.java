package com.domain.api;

import com.domain.api.config.Oauth2Properties;
import com.domain.api.core.controllers.ManagerRepository;
import com.domain.api.core.controllers.OAuthClientRepository;
import com.domain.api.core.controllers.RoleRepository;
import com.domain.api.core.domain.Manager;
import com.domain.api.core.domain.OAuthClient;
import com.domain.api.core.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Profile({"default"})
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private OAuthClientRepository oAuthClientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @Autowired
    private Oauth2Properties oauth2Properties;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (null != roleRepository.findByName("ADMIN"))
            return;

        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("USER");
        createRoleIfNotFound("CLIENT");

        Role adminRole = roleRepository.findByName("ADMIN");
        Manager admin = new Manager("admin");
        admin.setPassword(passwordEncoder.encode("admin"));

        Set<Role> roleList = new HashSet<>(Arrays.asList(adminRole));
        admin.setRoleList(roleList);
        admin.setActive(true);
        managerRepository.save(admin);

        // test user
        Manager test = new Manager("ajt");
        test.setPassword(passwordEncoder.encode("ajt"));

        test.setRoleList(new HashSet<>(Arrays.asList(adminRole)));
        test.setActive(false);
        managerRepository.save(test);

        // Client OAuth 2 - Backoffice kopax
        String clientId = "backoffice";
        OAuthClient clientDomain = new OAuthClient();
        clientDomain.setClientId(clientId);
        clientDomain.setClientSecret(passwordEncoder.encode("backoffice"));

        ArrayList<String> authorizedGrandTypeList = new ArrayList<>();
        authorizedGrandTypeList.add(OAuthClient.GRANT_TYPE_AUTHORIZATION_CODE);
        authorizedGrandTypeList.add(OAuthClient.GRANT_TYPE_REFRESH_TOKEN);
        clientDomain.setAuthorizedGrantTypeList(authorizedGrandTypeList);

        Role clientRole = roleRepository.findByName("CLIENT");
        Set<Role> clientRoleList = new HashSet<>(Arrays.asList(clientRole));
        clientDomain.setRoleList(clientRoleList);

        ArrayList<String> scopeList = new ArrayList<>();
        scopeList.add(OAuthClient.SCOPE_READ);
        scopeList.add(OAuthClient.SCOPE_WRITE);
        scopeList.add(OAuthClient.SCOPE_TRUST);
        clientDomain.setScopeList(scopeList);
        clientDomain.setAutoApprove(true);

        Integer oneMinuteInSeconds = 60;

        ArrayList<String> registeredRedirectUriList = new ArrayList<>();

        if (environment.acceptsProfiles("default")) {
            registeredRedirectUriList.add("http://localhost:8080/");
            clientDomain.setAccessTokenValiditySeconds((int) (oneMinuteInSeconds * 60));
            clientDomain.setRefreshTokenValiditySeconds((int) (oneMinuteInSeconds * 3600));
        }
        clientDomain.setRegisteredRedirectUriList(registeredRedirectUriList);

        ArrayList<String> resourceIdList = new ArrayList<>();
        resourceIdList.add(oauth2Properties.getResource().getName());
        clientDomain.setResourceIdList(resourceIdList);

        clientDomain.setManager(admin);

        this.oAuthClientRepository.save(clientDomain);

    }


    @Transactional
    protected Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
