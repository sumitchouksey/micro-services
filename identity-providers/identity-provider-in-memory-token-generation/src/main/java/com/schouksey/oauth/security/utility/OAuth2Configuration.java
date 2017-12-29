package com.schouksey.oauth.security.utility;
/*
 * Application    : micro-services
 * Package Name   : com.schouksey.oauth.security.utility
 * Class Name     : OAuth2Configuration
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */
import lombok.*;
import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OAuth2Configuration {
    private Collection<String> resourceIds;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Collection<String> authorizedGrantType;
    private Collection<String> scopes;
    private Collection<String> registeredRedirectUris;
    private Collection<String> autoApproveScopes;
}
