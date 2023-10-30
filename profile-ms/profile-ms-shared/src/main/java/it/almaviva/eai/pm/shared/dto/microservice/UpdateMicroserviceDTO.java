package it.almaviva.eai.pm.shared.dto.microservice;

import it.almaviva.eai.pm.shared.dto.group.GroupDTO;
import it.almaviva.eai.pm.shared.dto.role.RoleDTO;
import lombok.Data;

import java.util.List;

@Data
public class UpdateMicroserviceDTO {
  List<RoleDTO> newRoles;
  List<RoleDTO> deletedRoles;
  List<GroupDTO> newGroups;
  List<GroupDTO> deletedGroups;
}
