package com.schouksey.oauth.security.utility;
/*
 * Application    : micro-services
 * Package Name   : com.schouksey.oauth.security.utility
 * Class Name     : OAuth2ConfigProcessor
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OAuth2ConfigProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @param oauthNode
     *          - A ObjectNode representing JSON structure of oauth2 configuration
     * @return OAuth2Configuration
     *          - Return instance of {@link OAuth2Configuration}
     */
    public OAuth2Configuration getOAuth2Configuration(ObjectNode  oauthNode){
        OAuth2Configuration auth2Configuration  = new OAuth2Configuration();
        if(oauthNode!=null){
            if(oauthNode.get("accessTokenValiditySeconds")!=null)
                auth2Configuration.setAccessTokenValiditySeconds(Integer.parseInt(oauthNode.get("accessTokenValiditySeconds").asText()));
            if(oauthNode.get("refreshTokenValiditySeconds")!=null)
                auth2Configuration.setRefreshTokenValiditySeconds(Integer.parseInt(oauthNode.get("refreshTokenValiditySeconds").asText()));
            if(oauthNode.get("authorizedGrantTypes")!=null)
                auth2Configuration.setAuthorizedGrantType(_convertToStringList(oauthNode.get("authorizedGrantTypes")));
            if(oauthNode.get("scopes")!=null)
                auth2Configuration.setScopes(_convertToStringList(oauthNode.get("scopes")));
            if(oauthNode.get("resourceIds")!=null)
                auth2Configuration.setResourceIds(_convertToStringList(oauthNode.get("resourceIds")));
            if(oauthNode.get("registeredRedirectUris")!=null)
                auth2Configuration.setRegisteredRedirectUris(_convertToStringList(oauthNode.get("registeredRedirectUris")));
            if(oauthNode.get("autoApproveScopes")!=null)
                auth2Configuration.setRegisteredRedirectUris(_convertToStringList(oauthNode.get("autoApproveScopes")));
            return auth2Configuration;
        }
        return null;
    }

    private List<String> _convertToStringList(JsonNode jsonNode){
        List<String> stringList  = new ArrayList<>();
        ArrayNode arrayNode  = (ArrayNode) jsonNode;
        arrayNode.forEach(e->{
            stringList.add(e.asText());
        });
        return stringList;
    }

}
