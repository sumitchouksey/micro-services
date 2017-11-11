package com.schouksey.identity.provider.entity;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.identity.provider.entity
 * Class Name     : UserEntity
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
@Table(name = "user")
@EqualsAndHashCode
public class UserEntity implements BaseEntity{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="userId")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="mobile")
    private String mobile;

    @Column(name="password")
    private String password;

    @Column(name="dateOfBirth")
    private Date dateOfBirth;

    @Column(name="address")
    private String address;

    @Column(name="profilePic")
    private String profilePic;

    @Column(name="createdOn")
    private Timestamp createdOn;

    @Column(name="modifiedOn")
    private Timestamp modifiedOn;

    @Column(name= "passwordCreatedOn")
    private Timestamp passwordCreatedOn;

    @Column(name="attempts")
    private Integer attempts;

    @Column(name="isActive")
    @Type(type="org.hibernate.type.NumericBooleanType")
    private Boolean isActive;

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name="users_roles",
            joinColumns={@JoinColumn(name="userId", referencedColumnName="userId")},
            inverseJoinColumns={@JoinColumn(name="roleId", referencedColumnName="roleId")})
    private Set<RolesEntity> rolesEntity;
}
