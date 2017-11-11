package com.schouksey.config.server.app;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.config.server.app
 * Class Name     : ConfigServerApplication
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : This application is used to provide central and externalized configuration for all environments
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
// Enable config server
@EnableConfigServer
public class ConfigServerApplication extends SpringBootServletInitializer{

    /**
     * An Entry point for any Boot applications
     * <br> Used for JAR creations
     * @param args
     */
    public static void main(String args[]){
        SpringApplication.run(ConfigServerApplication.class,args);
    }

    /**
     * This method is used to generate WAR of application
     * <br> No to maintain any deployment descriptor file (web.xml)
     * @param builder
     * @return SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ConfigServerApplication.class);
    }
}
