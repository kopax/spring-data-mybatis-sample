package com.domain.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("api.oauth2")
public class Oauth2Properties {

    private Resource resource;
    private Authorization authorization;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public static class Resource {

        private String name;
        private String publickey;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPublickey() {
            return publickey;
        }

        public void setPublickey(String publickey) {
            this.publickey = publickey;
        }

    }

    public static class Authorization {

        private String jks;
        private String jkspass;
        private String alias;

        public String getJks() {
            return jks;
        }

        public void setJks(String jks) {
            this.jks = jks;
        }

        public String getJkspass() {
            return jkspass;
        }

        public void setJkspass(String jkspass) {
            this.jkspass = jkspass;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }

}
