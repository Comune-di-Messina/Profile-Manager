package it.almaviva.eai.pm.integration.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@Data
@ToString(exclude = {"domain", "groups"})
@EqualsAndHashCode(exclude = {"domain", "groups"})
@Entity
@Table(name = "TAB_PM_DOMAIN_VALUE")
public class DomainvalueEntity {

	@Setter(AccessLevel.NONE)
	@Id
	@GeneratedValue
	@Column
	private UUID id;

	@Column(nullable = false, length = 64)
	private String value;

	@Column
	private String description;

	//inverse side
	@ManyToMany(mappedBy = "domainvalues")
	private Set<GroupEntity> groups;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "domain_id", nullable = false)
	private DomainEntity domain;

}
