package it.almaviva.eai.pm.integration.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@ToString(exclude = {"microservices","domainvalues"})
@EqualsAndHashCode(exclude = {"microservices","domainvalues"})
@Table(name = "TAB_PM_GROUP")
public class GroupEntity {

	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue
	@Column
	private UUID id;

	@Column(nullable = false, length = 64)
	private String name;

	@Column(nullable = false, length = 64)
	private String wso2name;

	@ManyToMany(mappedBy = "groups")
	private Set<MicroserviceEntity> microservices = new HashSet<>();

	//owner
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "TAB_PM_GROUP_DOMAIN_VALUE",
			joinColumns = @JoinColumn(name = "group_id"),
			inverseJoinColumns = @JoinColumn(name = "domain_value_id"))
	private Set<DomainvalueEntity> domainvalues;


	//HELPER METHODS

	public void addDomainValue(DomainvalueEntity domainValueEntity){
		if(this.domainvalues == null){
			this.domainvalues = new HashSet<>();
		}
		this.domainvalues.add(domainValueEntity);
		domainValueEntity.getGroups().add(this);
	}

	public void removeDomainValue(DomainvalueEntity domainValueEntity){
		this.domainvalues.remove(domainValueEntity);
		domainValueEntity.getGroups().remove(this);
	}

	//HELPER METHODS

	public void addMicroservice(MicroserviceEntity microserviceEntity){
		if(this.microservices == null){
			this.microservices = new HashSet<>();
		}
		this.microservices.add(microserviceEntity);
		microserviceEntity.getGroups().add(this);
	}

	public void removeMicroservice(MicroserviceEntity microserviceEntity){
		this.microservices.remove(microserviceEntity);
		microserviceEntity.getGroups().remove(this);
	}


}
