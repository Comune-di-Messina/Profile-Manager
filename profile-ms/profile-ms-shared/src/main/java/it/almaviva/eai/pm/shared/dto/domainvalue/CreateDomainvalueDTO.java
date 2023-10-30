package it.almaviva.eai.pm.shared.dto.domainvalue;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateDomainvalueDTO {
  @NotNull
  private String value;
  private String desc;
}
