package com.schouksey.oauth.security.utility;
/*
 * Application    : micro-services
 * Package Name   : com.schouksey.oauth.security.utility
 * Class Name     : OAuth2ConfigProcessor
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OAuth2ConfigProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @param jsonConfiguration
     *          - A String representing JSON structure of oauth2 configuration
     * @return OAuth2Configuration
     *          - Return instance of {@link OAuth2Configuration}
     */
    public OAuth2Configuration getOAuth2Configuration(String jsonConfiguration){
        ObjectNode object;
        OAuth2Configuration auth2Configuration  = new OAuth2Configuration();
        try{
            if(jsonConfiguration!=null){
                object = (ObjectNode) objectMapper.readTree(jsonConfiguration);
                if(object.get("accessTokenValiditySeconds")!=null)
                    auth2Configuration.setAccessTokenValiditySeconds(Integer.parseInt(object.get("accessTokenValiditySeconds").asText()));
                if(object.get("refreshTokenValiditySeconds")!=null)
                    auth2Configuration.setRefreshTokenValiditySeconds(Integer.parseInt(object.get("refreshTokenValiditySeconds").asText()));
                if(object.get("authorizedGrantTypes")!=null)
                    auth2Configuration.setAuthorizedGrantType(_convertToStringList(object.get("authorizedGrantTypes")));
                if(object.get("scopes")!=null)
                    auth2Configuration.setScopes(_convertToStringList(object.get("scope")));
                if(object.get("resourceIds")!=null)
                    auth2Configuration.setResourceIds(_convertToStringList(object.get("resourceIds")));
                if(object.get("registeredRedirectUris")!=null)
                    auth2Configuration.setRegisteredRedirectUris(_convertToStringList(object.get("registeredRedirectUris")));
                if(object.get("autoApproveScopes")!=null)
                    auth2Configuration.setRegisteredRedirectUris(_convertToStringList(object.get("autoApproveScopes")));
                return auth2Configuration;
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
