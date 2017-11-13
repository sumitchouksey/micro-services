package com.schouksey.identity.provider.utility;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.utility
 * Enum Name      : ResponseConstant
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseConstant {
    CLIENT_IS_NULL("client-is-null"),
    USER_DOESNOT_BELONG_TO_ORGANIZATION("user-doesn't-belong-to-organization"),
    CLIENT_NOT_FOUND("client-not-found"),
    CLIENT_IS_EMPTY("client-is-empty"),
    AUTHORIZATION_HEADER_NULL("authorization-header-is-null"),
    ERROR_IN_PROCESSING_REQUEST("error-in-processing-request"),
    SUCCESS("success"),
    USER_NOT_FOUND("user-not-found"),
    USER_IS_INACTIVE("user-is-inactive"),
    ATTEMPT_LIMIT_EXCEEDED("attempts-more-than-5"),
    INVALID_PASSWORD("invalid-password"),
    EMAIL_ID_EMPTY("email-id-is-empty"),
    EMAIL_ID_NULL("email-id-is-null"),
    PASSWORD_IS_NULL("password-is-null"),
    PASSWORD_IS_EMPTY("password-is-empty"),
    IMPROPER_PASSWORD_PATTERN("improper-password-pattern"),
    ;
    private String status;
    public String getStatus(){
        return this.status;
    }

}
