package com.schouksey.identity.provider.app;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.app
 * Class Name     : IdentityProviderApplication
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
// Scanning of External Packages always Required
@ComponentScan(value = {
        "com.schouksey.oauth.security.config",
        "com.schouksey.oauth.security.custom.config",
        "com.schouksey.identity.provider.controllers",
        "com.schouksey.identity.provider.datasource.config",
        "com.schouksey.identity.provider.datasource.config.util",
        "com.schouksey.identity.provider.service",
        "com.schouksey.identity.provider.repository",
        "com.schouksey.identity.provider.utility"
})
// Enable Resource Server to validate tokens and get session details via token
@EnableResourceServer
//Enable Eureka Client
@EnableEurekaClient
public class IdentityProviderApplication extends SpringBootServletInitializer{

    /**
     * An Entry point for any Boot applications
     * <br> Used for JAR creations
     * @param args
     */
    public static void main(String args[]){
        SpringApplication.run(IdentityProviderApplication.class);
    }

    /**
     * This method is used to generate WAR of application
     * <br> No to maintain any deployment descriptor file (web.xml)
     * @param builder
     * @return SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(IdentityProviderApplication.class);
    }
}
