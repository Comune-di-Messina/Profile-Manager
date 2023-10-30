package it.almaviva.eai.um.api;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Authorization API", authorizations =  {@Authorization(value = "X-Auth-Token")})
public interface IAuthorizationApi {

  @ApiOperation(value = "This function authorizes the given role to perform the specified action on the given resource", nickname = "authorizeRole")
  @PutMapping(value = "/admin/roles/resources", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void authorizeRole(@RequestParam("rolename")@ApiParam(required = true)String roleName, @RequestParam("resourceid")@ApiParam(required = true)String resourceId);

  @ApiOperation(value = "This function clears all authorizations of the role", nickname = "clearAllRoleAuthorization")
  @PatchMapping(value = "/admin/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void clearAllRoleAuthorization(@RequestParam("rolename")@ApiParam(required = true)String roleName);

  @ApiOperation(value = "This function clears all the authorizations for the given resource", nickname = "clearResourceAuthorizations")
  @PatchMapping(value = "/admin/resources", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void clearResourceAuthorizations(@RequestParam("resourceid")@ApiParam(required = true)String resourceId);

  @ApiOperation(value = "This function clear the authorization of the specified role to perform the given action on the resource", nickname = "clearRoleAuthorization")
  @PatchMapping(value = "/admin/roles/resources", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void clearRoleAuthorization(@RequestParam("rolename")@ApiParam(required = true)String roleName, @RequestParam("resourceid")@ApiParam(required = true)String resourceId);

  @ApiOperation(value = "This function removes the authorization of the role to perform the given action on the specified resource", nickname = "denyRole")
  @DeleteMapping(value = "/admin/roles/resources", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void denyRole(@RequestParam("rolename")@ApiParam(required = true)String roleName, @RequestParam("resourceid")@ApiParam(required = true)String resourceId);

  @ApiOperation(value = "This function retrieves the list of authorized roles to perform the given action on the specified resource", nickname = "getAllowedRolesForResource",  response = String.class, responseContainer = "List")
  @GetMapping(value = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Valid Roles are found",  response = String.class, responseContainer = "List")})
  @ResponseStatus(HttpStatus.OK)
  List<String> getAllowedRolesForResource(@RequestParam("resourceid")@ApiParam(required = true)String resourceId);

  @ApiOperation(value = "This function checks whether the given role is authorized to perform the action on the specified resource", nickname = "isRoleAuthorized", response = Boolean.class)
  @GetMapping(value = "/roles/resources", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  boolean isRoleAuthorized(@RequestParam("rolename")@ApiParam(required = true)String roleName, @RequestParam("resourceid")@ApiParam(required = true)String resourceId);

}
