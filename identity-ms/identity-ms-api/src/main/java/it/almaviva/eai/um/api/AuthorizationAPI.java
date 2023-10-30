package it.almaviva.eai.um.api;

import it.almaviva.eai.um.service.IAuthorizationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class AuthorizationAPI implements IAuthorizationApi {

  @Autowired
  private IAuthorizationService iAuthorizationService;

  @SneakyThrows
  @Override
  public void authorizeRole(String roleName, String resourceId) {
    iAuthorizationService.authorizeRole(roleName, resourceId);
  }

  @Override
  public void clearAllRoleAuthorization(String roleName) {
    iAuthorizationService.clearAllRoleAuthorization(roleName);
  }

  @Override
  public void clearResourceAuthorizations(String resourceId) {
    iAuthorizationService.clearResourceAuthorizations(resourceId);
  }

  @Override
  public void clearRoleAuthorization(String roleName, String resourceId) {
    iAuthorizationService.clearRoleAuthorization(roleName,resourceId);
  }

  @SneakyThrows
  @Override
  public void denyRole(String roleName, String resourceId) {
    iAuthorizationService.denyRole(roleName,resourceId);
  }

  @Override
  public List<String> getAllowedRolesForResource(String resourceId) {
    return iAuthorizationService.getAllowedRolesForResource(resourceId);
  }

  @Override
  public boolean isRoleAuthorized(String roleName, String resourceId) {
    return iAuthorizationService.isRoleAuthorized(roleName, resourceId);
  }
}
