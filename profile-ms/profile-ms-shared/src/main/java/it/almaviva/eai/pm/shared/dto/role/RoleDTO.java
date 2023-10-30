package it.almaviva.eai.pm.shared.dto.role;

import it.almaviva.eai.pm.shared.dto.action.ActionDTO;
import it.almaviva.eai.pm.shared.dto.permission.PermissionDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class RoleDTO implements Serializable {

  private UUID id;
  private String name;
  private String wso2name;
  private List<ActionDTO> actions;
  private List<PermissionDTO> permissions;

}
