package it.almaviva.eai.pm.api.controllers;

import io.swagger.annotations.*;
import it.almaviva.eai.pm.api.data.ActionData;
import it.almaviva.eai.pm.shared.dto.domain.CreateDomainDTO;
import it.almaviva.eai.pm.shared.dto.domainvalue.CreateDomainvalueDTO;
import it.almaviva.eai.pm.shared.dto.group.CreateGroupDTO;
import it.almaviva.eai.pm.shared.dto.microservice.CreateMicroserviceDTO;
import it.almaviva.eai.pm.shared.dto.role.CreateRoleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/admin")
@Api(value = "admin-controller", authorizations =  {@Authorization(value = "X-Auth-Token")})
public interface IAdminController {

  @ApiOperation(value = "Create new domain",  notes = "New domain", response = UUID.class, nickname = "insertDomain")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid domain is created", response = UUID.class)})
  @PostMapping(value = "/domains", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.CREATED)
  UUID insertDomain(@Valid @RequestBody  @ApiParam(required = true)CreateDomainDTO createDomainDTO);

  @ApiOperation(value = "Update domain", notes = "Update only the domain's fields", nickname = "updateDomain")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid domain is updated")})
  @PutMapping(value="/domains/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void updateDomain(@PathVariable("id") @ApiParam(required = true) UUID domainId, @Valid @RequestBody  @ApiParam(required = true) CreateDomainDTO createDomainDTO);

  @ApiOperation(value = "Delete domain", nickname = "deleteDomain", notes = "Returns true if the domain is successfully deleted")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid domain is deleted")})
  @DeleteMapping(value="/domains/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void deleteDomain(@PathVariable("id") @ApiParam(required = true) UUID domainId);

  @ApiOperation(value = "Add domainValue to domain", nickname = "insertDomainvalue", notes = "Returns HTTP 201 if the domainvalue is successfully added to domain exists", response = UUID.class)
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid domainvalue is created" ,response = UUID.class)})
  @PostMapping(value = "/domains/{id}/domainvalues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  UUID insertDomainvalue(@PathVariable("id") @ApiParam(required = true) UUID domainId,
      @RequestBody @ApiParam(required = true) CreateDomainvalueDTO createDomainvalueDTO);

  @ApiOperation(value = "Delete domainValue", nickname = "deleteDomainvalue", notes = "Returns true if the domainValue is successfully deleted.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid domainvalue is deleted")})
  @DeleteMapping(value="/domainvalues/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void deleteDomainvalue(@PathVariable("id") @ApiParam(required = true)UUID domainvalueId);

  @ApiOperation(value = "Create role", nickname = "insertRole", response = UUID.class)
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid role is created",response = UUID.class)})
  @PostMapping(value = "/microservices/{id}/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  UUID insertRole(@PathVariable("id")  @ApiParam(required = true) UUID microserviceId, @RequestBody  @ApiParam(required = true) CreateRoleDTO createRoleDTO) throws Exception;

  @ApiOperation(value = "Create group",  nickname = "insertGroup", response = UUID.class)
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid group is created",response = UUID.class)})
  @PostMapping(value = "/microservices/{id}/groups", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  UUID insertGroup(@PathVariable("id")  @ApiParam(required = true) UUID microserviceId, @ApiParam(required = true) @RequestBody CreateGroupDTO createGroupDTO) throws Exception;

  @ApiOperation(value = "Add microservice", nickname = "insertMicroservice",  notes = "Returns HTTP 201 if the microservice is successfully created",response = UUID.class)
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid microservice is created",response = UUID.class)})
  @PostMapping(value = "/microservices", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  UUID insertMicroservice(@RequestBody  @ApiParam(required = true) CreateMicroserviceDTO createMicroserviceDTO);

  @ApiOperation(value = "Delete microservice", nickname = "deleteMicroservice",  notes = "Returns true if the microservice is successfully deleted.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Valid microservice is deleted"),
      @ApiResponse(code = 404, message = "Valid microservice is not found")
  })
  @DeleteMapping(value="/microservices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void deleteMicroservice(@PathVariable("id")  @ApiParam(required = true) UUID microserviceId);

  @ApiOperation(value = "Delete role", nickname = "deleteRole", notes = "Returns true if the microservice is successfully deleted.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid microservice is deleted")})
  @DeleteMapping(value="/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void deleteRole(@PathVariable("id")  @ApiParam(required = true) UUID roleId) throws Exception;

  @ApiOperation(value = "Delete group",nickname = "deleteGroup",   notes = "Returns true if the microservice is successfully deleted.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid microservice is deleted")})
  @DeleteMapping(value="/groups/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void deleteGroup(@PathVariable("id")  @ApiParam(required = true) UUID groupId) throws Exception;

  @ApiOperation(value = "Update the association between group and domainvalue", nickname = "addDomainvalueToGroup")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Group is updated")})
  @PatchMapping(value="/groups/{groupId}/domainvalues/{domainvalueId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void addDomainvalueToGroup(@PathVariable("groupId") @ApiParam(required = true) UUID groupId, @PathVariable("domainvalueId")  @ApiParam(required = true) UUID domainvalueId);

  @ApiOperation(value = "Delete the association between group and domainvalue", nickname = "removeDomainvalueFromGroup")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Delete the association between group and domainvalue")})
  @DeleteMapping(value="/groups/{groupId}/domainvalues/{domainvalueId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void removeDomainvalueFromGroup(@PathVariable("groupId")  @ApiParam(required = true) UUID groupId,  @PathVariable("domainvalueId")  @ApiParam(required = true) UUID domainvalueId);

  @ApiOperation(value = "Update the association between group and microservice", nickname = "addMicroserviceToGroup")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Group is updated")})
  @PatchMapping(value="/groups/{groupId}/microservices/{microserviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void addMicroserviceToGroup(@PathVariable("groupId")  @ApiParam(required = true) UUID groupId, @PathVariable("microserviceId")  @ApiParam(required = true) UUID microserviceId);

  @ApiOperation(value = "Delete the association between group and microservice", nickname = "removeMicroserviceFromGroup")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Micorservice is removed from Group")})
  @DeleteMapping(value="/groups/{groupId}/microservices/{microserviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void removeMicroserviceFromGroup(@PathVariable("groupId")  @ApiParam(required = true) UUID groupId,  @ApiParam(required = true) @PathVariable("microserviceId") UUID microserviceId);

  @ApiOperation(value = "Update the association between role and microservice", nickname = "addMicroserviceToRole")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Role is updated")})
  @PatchMapping(value="/roles/{roleId}/microservices/{microserviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void addMicroserviceToRole(@PathVariable("roleId")@ApiParam(required = true) UUID roleId,@ApiParam(required = true) @PathVariable("microserviceId") UUID microserviceId);

  @ApiOperation(value = "Delete the association between role and microservice", nickname = "removeMicroserviceFromRole")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Delete the association between role and microservice")})
  @DeleteMapping(value="/roles/{roleId}/microservices/{microserviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void removeMicroserviceFromRole(@PathVariable("roleId") @ApiParam(required = true) UUID roleId, @ApiParam(required = true) @PathVariable("microserviceId") UUID microserviceId);

  @ApiOperation(value = "Create action",  nickname = "insertAction", response = UUID.class)
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid action is created",response = UUID.class)})
  @PostMapping(value = "/microservices/{id}/actions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  UUID insertAction(@PathVariable("id") @ApiParam(required = true) UUID microserviceId,@RequestBody @Valid @ApiParam(required = true) ActionData actionData);

  @ApiOperation(value = "Delete action",nickname = "deleteAction",   notes = "Returns true if the action is successfully deleted.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid action is deleted")})
  @DeleteMapping(value="/actions/{actionId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void deleteAction(@PathVariable("actionId") @ApiParam(required = true) UUID id);

  @ApiOperation(value = "Update the association between role and actions", nickname = "addActionToRole")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Role is updated")})
  @PatchMapping(value="/roles/{roleId}/actions", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void addActionToRole(@RequestParam("actionId") @ApiParam(required = true) List<UUID> actionId, @PathVariable("roleId") @ApiParam(required = true)  UUID roleId);

  @ApiOperation(value = "Delete the association between role and action", nickname = "removeActionFromRole")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Delete the association between role and microservice")})
  @DeleteMapping(value="/roles/{roleId}/actions/{actionId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void removeActionFromRole(@PathVariable("actionId") @ApiParam(required = true) UUID actionId,@PathVariable("roleId") @ApiParam(required = true)   UUID roleId);


}
