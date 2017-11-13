package com.schouksey.identity.provider.service;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.service
 * Class Name     : IdenityProviderService
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */

import com.schouksey.identity.provider.entity.ClientEntity;
import com.schouksey.identity.provider.entity.UserEntity;
import com.schouksey.identity.provider.repository.IdentityProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IdentityProviderService {

    @Autowired
    private IdentityProviderRepository identityProviderRepository;

    /**
     * Fetch user details on the basis of user email
     * @param email
     *          - user email whose details to be fetched
     * @return UserEntity
     *          - Return UserEntity from database table for given user email
     * @author Sumit Chouksey  "sumitchouksey2315@gmail.com"
     */
    public UserEntity getUserEntity(String email) {
        return identityProviderRepository.getUserEntity(email);
    }

    /**
     * Save new UserEntity or Update existing UserEntity
     * @param userEntity
     *          - userEntity - user details to be saved or updated
     * @author Sumit Chouksey  "sumitchouksey2315@gmail.com"
     */
    public void saveOrUpdateUserEntity(UserEntity userEntity){
        identityProviderRepository.saveOrUpdateUserEntity(userEntity);
    }

    /**
     * Fetch client details on the basis of client name
     * @param clientName
     *          - clientName whose details to be fetched
     * @return ClientEntity
     *          - Return ClientEntity from database table for given clientName
     * @author Sumit Chouksey  "sumitchouksey2315@gmail.com"
     */
    public ClientEntity getClientEntity(String clientName){
        return identityProviderRepository.getClientEntity(clientName);
    }
}
