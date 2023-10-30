package it.almaviva.eai.pm.integration.entity;

import lombok.*;

import javax.persistence.*;

/**
 * 	id uuid not null primary key,
 * 	name varchar(255) null,
 * 	description varchar(255) null,
 * 	value varchar(255) null,
 * 	matchrule varchar(255) null,
 * 	method varchar(255) null
 * 	id_role integer not null
 */
@Data
@ToString(exclude = {"role"})
@EqualsAndHashCode(exclude = {"role"})
@Entity
@Table(name = "TAB_PM_PERMISSION")
public class PermissionEntity {

    @Setter(AccessLevel.NONE)
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column
    private String description;

    @Column(nullable = false)
    private String matchrule;

    @Column(nullable = false, length = 64)
    private String httpmethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

}
