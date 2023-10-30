package it.almaviva.eai.pm.integration.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@ToString(exclude = {"microservice", "actions"})
@EqualsAndHashCode(exclude = {"microservice", "actions"})
@Entity
@Table(name = "TAB_PM_ROLE")
public class RoleEntity {

	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue
	@Column
	private UUID id;

	@Column(nullable = false, length = 64)
	private String name;

	@Column(nullable = false, length = 64)
	private String wso2name;

	@ManyToMany(mappedBy = "roles")
	private Set<MicroserviceEntity> microservice = new HashSet<>();

	@ManyToMany(mappedBy = "roles")
	private Set<ActionEntity> actions = new HashSet<>();

	@OneToMany(mappedBy = "role" )
	private Set<PermissionEntity> permissions = new HashSet<>();


	public void addMicroservice(MicroserviceEntity microserviceEntity){
		if(this.microservice == null){
			this.microservice = new HashSet<>();
		}
		this.microservice.add(microserviceEntity);
		microserviceEntity.getRoles().add(this);
	}

	public void removeMicroservice(MicroserviceEntity microserviceEntity){
		this.microservice.remove(microserviceEntity);
		microserviceEntity.getRoles().remove(this);
	}

	public void addAction(ActionEntity actionEntity){
		if(this.actions == null){
			this.actions = new HashSet<>();
		}
		this.actions.add(actionEntity);
		actionEntity.getRoles().add(this);
	}

	public void removeAction(ActionEntity actionEntity){
		this.actions.remove(actionEntity);
		actionEntity.getRoles().remove(this);
	}

	public void addPermission(PermissionEntity permissionEntity){
		if(this.permissions == null){
			this.permissions = new HashSet<>();
		}
		this.permissions.add(permissionEntity);
		permissionEntity.setRole(this);
	}


}
