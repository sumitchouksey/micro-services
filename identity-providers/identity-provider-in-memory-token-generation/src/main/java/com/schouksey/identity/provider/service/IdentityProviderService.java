package com.schouksey.identity.provider.service;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.service
 * Class Name     : IdenityProviderService
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */

import com.schouksey.identity.provider.repository.IdentityProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IdentityProviderService {
    @Autowired
    private IdentityProviderRepository identityProviderRepository;
}
