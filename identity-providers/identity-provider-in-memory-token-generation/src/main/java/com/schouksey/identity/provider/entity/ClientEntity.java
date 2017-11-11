package com.schouksey.identity.provider.entity;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.entity
 * Class Name     : ClientEntity
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */

import com.schouksey.persistence.api.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class ClientEntity implements BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "clientId")
    private Long id;

    @Column(name = "clientName")
    private String clientName;

    @Column(name = "parentClientId")
    private Long parentClientId;

    @Column(name = "configuration")
    private String configuration;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @Column(name = "modifiedOn")
    private Timestamp modifiedOn;

    @Column(name = "isActive")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isActive;

    @OneToOne(mappedBy = "clientEntity")
    private Set<RolesEntity> rolesEntitySet;
}
