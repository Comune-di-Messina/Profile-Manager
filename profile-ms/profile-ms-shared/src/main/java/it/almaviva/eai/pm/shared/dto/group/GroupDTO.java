package it.almaviva.eai.pm.shared.dto.group;

import it.almaviva.eai.pm.shared.dto.domainvalue.DomainvalueDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class GroupDTO implements Serializable {

  private UUID id;
  private String name;
  private String wso2name;
  private List<DomainvalueDTO> domainvalues;
}
