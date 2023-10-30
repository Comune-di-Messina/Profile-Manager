package it.almaviva.eai.pm.integration.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@ToString(exclude = {"roles","groups", "actions"})
@EqualsAndHashCode(exclude = {"roles","groups","actions"})
@Entity
@Table(name = "TAB_PM_MICRO_SERVICE")
public class MicroserviceEntity {

	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue
	@Column
	private UUID id;

	@Column(nullable = false, length = 64, unique = true)
	private String name;

	@Column
	private String description;

	@ManyToMany
    @JoinTable(
			name = "TAB_PM_MS_ROLE",
			joinColumns = @JoinColumn(name = "ms_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles;

	@ManyToMany
    @JoinTable(
			name = "TAB_PM_MS_GROUP",
			joinColumns = @JoinColumn(name = "ms_id"),
			inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<GroupEntity> groups;


	@OneToMany(mappedBy = "microservice" , orphanRemoval = true)
	private Set<ActionEntity> actions;


	// HELPER METHODS

	public void addRole(RoleEntity  roleEntity){
	  if(this.roles == null){
	    this.roles = new HashSet<>();
    }
	  this.roles.add(roleEntity);
	  roleEntity.getMicroservice().add(this);
  }

  public void removeRole(RoleEntity roleEntity){
	  this.roles.remove(roleEntity);
	  roleEntity.getMicroservice().remove(this);
  }

  public void addGroup(GroupEntity  groupEntity){
    if(this.groups == null){
      this.groups = new HashSet<>();
    }
    this.groups.add(groupEntity);
    groupEntity.getMicroservices().add(this);
  }

  public void removeGroup(GroupEntity groupEntity){
    this.groups.remove(groupEntity);
    groupEntity.getMicroservices().remove(this);
  }

	public void addAction(ActionEntity actionEntity){
		if(this.actions == null){
			this.actions = new HashSet<>();
		}
		this.actions.add(actionEntity);
		actionEntity.setMicroservice(this);
	}



}
