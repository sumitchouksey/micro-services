package com.schouksey.discovery.server.app;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.discovery.server.app
 * Class Name     : DiscoveryServerApplication
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : This application behaves as a discovery server which discover the new registrants(apis or services)
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
// Enable eureka server
@EnableEurekaServer
public class DiscoveryServerApplication extends SpringBootServletInitializer{

    /**
     * An Entry point for any Boot applications
     * <br> Used for JAR creations
     * @param args
     */
    public static void main(String args[]){
        SpringApplication.run(DiscoveryServerApplication.class);
    }

    /**
     * This method is used to generate WAR of application
     * <br> No to maintain any deployment descriptor file (web.xml)
     * @param builder
     * @return SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DiscoveryServerApplication.class);
    }
}
