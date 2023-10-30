package it.almaviva.eai.um.service;

import java.util.List;

public interface IAuthorizationService {

  void authorizeRole(String roleName, String resourceId) throws Exception;

  void clearAllRoleAuthorization(String roleName);

  void clearResourceAuthorizations(String resourceId);

  void clearRoleAuthorization(String roleName, String resourceId);

  void denyRole(String roleName, String resourceId) throws Exception;

  List<String> getAllowedRolesForResource(String resourceId);

  boolean isRoleAuthorized(String roleName, String resourceId);
}
