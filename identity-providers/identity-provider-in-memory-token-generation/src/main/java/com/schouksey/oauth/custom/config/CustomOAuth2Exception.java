package com.schouksey.oauth.custom.config;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.oauth.custom.config
 * Class Name     : CustomOAuth2Exception
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : An implementation of OAuth2Exception
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using=CustomOAuth2ExceptionSerializer.class)
public class CustomOAuth2Exception extends OAuth2Exception{
    /**
     * Parameterized Contructor to initialize super class exception object with given error message
     * @param errorMessage
     *          - An errorMessage representing error details
     * @author Sumit Chouksey "sumitchouksey23152gmail.com"
     */
    public CustomOAuth2Exception(String errorMessage) {
        super(errorMessage);
    }

    /**
     * This method return CustomOAuth2Exception instance which contains productDetails, statusDetails & error status
     *
     * @param errorMessage
     *          - A description about the exception
     * @param statusCode
     *          - status code of error
     * @param statusValue
     *          - the status message of exception
     * @return CustomOAuth2Exception
     *          -  Return CustomOAuth2Exception instance
     * @author Sumit Chouksey "sumitchouksey23152gmail.com"
     */
    public static CustomOAuth2Exception getCustomOAuth2Exception(String errorMessage , String statusCode , String statusValue)
    {
        CustomOAuth2Exception customOAuth2Exception  = new CustomOAuth2Exception(errorMessage);
        customOAuth2Exception.addAdditionalInformation("code",statusCode);
        customOAuth2Exception.addAdditionalInformation("value",statusValue);
        return customOAuth2Exception;
    }
}
