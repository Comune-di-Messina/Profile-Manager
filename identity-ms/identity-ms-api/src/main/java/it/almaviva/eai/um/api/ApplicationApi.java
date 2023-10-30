package it.almaviva.eai.um.api;

import it.almaviva.eai.um.common.dto.role.RoleUpdateDTO;
import it.almaviva.eai.um.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class ApplicationApi implements IApplicationApi {


  @Autowired
  private IRoleService iRoleService;

  @Override
  public List<String> getApplications() {
    List<String> allRoles = iRoleService.getAllRoles();
    allRoles.removeIf(s -> !s.startsWith("Application/"));
    return allRoles;
  }

  @Override
  public List<String> getUsersOfApplications(String applicationId) {
    return iRoleService.getUserListOfRole(applicationId);
  }

  @Override
  public void updateApplication(String applicationId, RoleUpdateDTO roleUpdateDTO) {
    iRoleService.updateRole(applicationId, roleUpdateDTO);
  }


}
