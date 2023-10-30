package it.almaviva.eai.pm.shared.dto.domainvalue;

import it.almaviva.eai.pm.shared.dto.domain.DomainDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString(exclude = {"domain"})
@EqualsAndHashCode(exclude = {"domain"})
public class DomainvalueDTO {
  private UUID id;
  private String value;
  private String description;
  private DomainDTO domain;
}
