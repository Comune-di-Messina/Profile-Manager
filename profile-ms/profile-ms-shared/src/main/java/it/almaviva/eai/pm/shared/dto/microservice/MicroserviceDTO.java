package it.almaviva.eai.pm.shared.dto.microservice;

import it.almaviva.eai.pm.shared.dto.action.ActionDTO;
import it.almaviva.eai.pm.shared.dto.group.GroupDTO;
import it.almaviva.eai.pm.shared.dto.role.RoleDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MicroserviceDTO {

  private UUID id;
  private String name;
  private String description;

  private List<RoleDTO> roles;
  private List<GroupDTO> groups;
  private List<ActionDTO> actions;

}
