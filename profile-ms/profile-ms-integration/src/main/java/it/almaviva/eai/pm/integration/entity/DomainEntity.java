package it.almaviva.eai.pm.integration.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@ToString(exclude = {"domainvalues"})
@EqualsAndHashCode(exclude = {"domainvalues"})
@Entity
@Table(name = "TAB_PM_DOMAIN")
public class DomainEntity {

	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue
	@Column
	private UUID id;

	@Column(nullable = false, length = 64)
	private String name;

	@Column
	private String description;

	@Column(nullable = false, length = 64)
	private String wso2name;

	@OneToMany(mappedBy = "domain" , orphanRemoval = true)
	private Set<DomainvalueEntity> domainvalues;


	// HELPER METHODS
	public void addDomainvalue(DomainvalueEntity domainvalueEntity){
		if(this.domainvalues == null){
			domainvalues = new HashSet<>();
		}
		this.domainvalues.add(domainvalueEntity);
		domainvalueEntity.setDomain(this);
	}

}
