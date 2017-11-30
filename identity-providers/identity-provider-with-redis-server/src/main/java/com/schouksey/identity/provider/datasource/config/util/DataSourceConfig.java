package com.schouksey.identity.provider.datasource.config.util;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.datasource.config.util
 * Class Name     : DataSourceConfig
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/28/2017
 * Description    : This class is useful to retrieve the yml datasource tag properties and the value automatically initialized inside private fields of DataSourceConfiguration class
 */


import com.schouksey.persistence.api.configurations.DataSourceConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dataSources.platform")
@RefreshScope
public class DataSourceConfig extends DataSourceConfiguration {
}
