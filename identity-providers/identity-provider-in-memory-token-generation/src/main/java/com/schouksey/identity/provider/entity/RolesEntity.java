package com.schouksey.identity.provider.entity;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.entity
 * Class Name     : RolesEntity
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : TODO
 */

import com.schouksey.persistence.api.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
@EqualsAndHashCode
public class RolesEntity implements BaseEntity{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="roleId")
    private Long id;

    @Column(name="roleName")
    private String roleName;

    @Column(name="createdOn")
    private Timestamp createdOn;

    @Column(name="modifiedOn")
    private Timestamp modifiedOn;

    @Column(name="isActive")
    @Type(type="org.hibernate.type.NumericBooleanType")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private ClientEntity clientEntity;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name="users_roles",
            joinColumns={@JoinColumn(name="roleId", referencedColumnName="roleId")},
            inverseJoinColumns={@JoinColumn(name="userId", referencedColumnName="userId")})
    private Set<UserEntity> userEntities;
}
