package com.schouksey.identity.provider.repository;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.repository
 * Class Name     : IdentityProviderRepository
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */

import com.schouksey.identity.provider.entity.ClientEntity;
import com.schouksey.identity.provider.entity.ClientEntity_;
import com.schouksey.persistence.api.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class IdentityProviderRepository extends BaseRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Fetch client details on the basis of client name
     * @param clientName
     *          - clientName whose details to be fetched
     * @return ClientEntity
     *          - Return ClientEntity from database table for given clientName
     * @author Sumit Chouksey  "sumitchouksey2315@gmail.com"
     */
    public ClientEntity getClientEntity(String clientName){
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> clientEntityCriteriaQuery  = criteriaBuilder.createQuery(ClientEntity.class);
        Root<ClientEntity> clientEntityRoot = clientEntityCriteriaQuery.from(ClientEntity.class);
        clientEntityCriteriaQuery.select(clientEntityRoot).distinct(true);
        clientEntityCriteriaQuery.where(
            criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.clientName),clientName),
            criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.isActive),true)
        );
        List<ClientEntity> clientEntityList = entityManager.createQuery(clientEntityCriteriaQuery).getResultList();
        if(clientEntityList!=null){
            if(!clientEntityList.isEmpty())
                return clientEntityList.get(0);
        }
        return null;
    }

    /**
     * Fetch client details on the basis of client id
     * @param clientId
     *          - clientId whose details to be fetched
     * @return ClientEntity
     *          - Return ClientEntity from database table for given clientName
     * @author Sumit Chouksey  "sumitchouksey2315@gmail.com"
     */
    public ClientEntity getClientEntity(Long clientId){
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> clientEntityCriteriaQuery  = criteriaBuilder.createQuery(ClientEntity.class);
        Root<ClientEntity> clientEntityRoot = clientEntityCriteriaQuery.from(ClientEntity.class);
        clientEntityCriteriaQuery.select(clientEntityRoot).distinct(true);
        clientEntityCriteriaQuery.where(
                criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.clientName),clientId),
                criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.isActive),true)
        );
        List<ClientEntity> clientEntityList = entityManager.createQuery(clientEntityCriteriaQuery).getResultList();
        if(clientEntityList!=null){
            if(!clientEntityList.isEmpty())
                return clientEntityList.get(0);
        }
        return null;
    }

}
