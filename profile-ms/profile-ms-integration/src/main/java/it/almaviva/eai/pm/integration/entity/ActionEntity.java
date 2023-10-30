package it.almaviva.eai.pm.integration.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@ToString(exclude = {"roles","microservice"})
@EqualsAndHashCode(exclude = {"roles","microservice"})
@Entity
@Table(name = "TAB_PM_ACTION",uniqueConstraints = @UniqueConstraint(columnNames = {"name","ms_id"}))
public class ActionEntity {

  @Setter(AccessLevel.NONE)
  @Id
  @GeneratedValue
  @Column
  private UUID id;

  @Column(nullable = false, length = 64)
  private String name;

  @Column
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ms_id", nullable = false)
  private MicroserviceEntity microservice;

  @ManyToMany
  @JoinTable(
      name = "TAB_PM_ROLE_ACTION",
      joinColumns = @JoinColumn(name = "action_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles;

}
