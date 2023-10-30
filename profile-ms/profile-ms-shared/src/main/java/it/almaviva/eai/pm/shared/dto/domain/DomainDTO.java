package it.almaviva.eai.pm.shared.dto.domain;

import it.almaviva.eai.pm.shared.dto.domainvalue.DomainvalueDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Data
@ToString(exclude = {"domainvalues"})
@EqualsAndHashCode(exclude = {"domainvalues"})
public class DomainDTO {
  private UUID id;
  private String name;
  private String description;
  private String wso2name;
  private Set<DomainvalueDTO> domainvalues;
}
