package com.schouksey.identity.provider.service;
/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.service
 * Class Name     : CustomClientDetailsService
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : Custom Client Detail Service
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schouksey.identity.provider.entity.ClientEntity;
import com.schouksey.identity.provider.repository.IdentityProviderRepository;
import com.schouksey.identity.provider.utility.ResponseConstant;
import com.schouksey.oauth.security.custom.config.CustomOAuth2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class CustomClientDetailsService implements ClientDetailsService{

    @Autowired
    private IdentityProviderRepository identityProviderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        if(clientId==null)
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.CLIENT_IS_NULL.getStatus(),"400","Bad Request");
        else if(org.apache.commons.lang.StringUtils.isBlank(clientId))
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.CLIENT_IS_EMPTY.getStatus(),"400","Bad Request");
        ClientEntity clientEntity  = identityProviderRepository.getClientEntity(clientId);
        if(clientEntity==null)
            throw CustomOAuth2Exception.getCustomOAuth2Exception(ResponseConstant.CLIENT_NOT_FOUND.getStatus(),"404","Not Found");

        BaseClientDetails baseClientDetails  = new BaseClientDetails();
        baseClientDetails.setClientId(clientId);
        String configurations  = clientEntity.getConfiguration();
        JsonNode jsonNode;
        if(configurations!=null){
            try{
                jsonNode = objectMapper.readTree(configurations);
                if(jsonNode!=null){
                   JsonNode oauthNode = jsonNode.get("oauthConfig");
                   baseClientDetails.setClientId(clientId);
                   baseClientDetails.setAccessTokenValiditySeconds(Integer.parseInt(oauthNode.get("accessTokenValiditySeconds").asText()));
                   baseClientDetails.setRefreshTokenValiditySeconds(Integer.parseInt(oauthNode.get("refreshTokenValiditySeconds").asText()));
                   baseClientDetails.setScope(Arrays.asList(oauthNode.get("scope").asText()));
                   baseClientDetails.setAuthorizedGrantTypes(Arrays.asList("password","refresh_token","authorization_code"));
                   return baseClientDetails;
                }
            }
            catch (Exception e){

            }
        }
        return null;
    }
}
