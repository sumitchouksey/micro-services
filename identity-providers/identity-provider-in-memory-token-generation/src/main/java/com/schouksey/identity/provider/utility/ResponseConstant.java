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
    CLIENT_NOT_FOUND("client-not-found"),
    CLIENT_IS_EMPTY("client-is-empty");
    private String status;
    public String getStatus(){
        return this.status;
    }

}
