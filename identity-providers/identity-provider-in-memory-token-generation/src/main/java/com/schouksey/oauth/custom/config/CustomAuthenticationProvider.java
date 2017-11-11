package com.schouksey.oauth.custom.config;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.oauth.custom.config
 * Class Name     : CustomAuthenticationProvider
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */


import com.schouksey.identity.provider.service.IdentityProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider implements AuthenticationProvider{
    @Autowired
    private IdentityProviderService identityProviderService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
