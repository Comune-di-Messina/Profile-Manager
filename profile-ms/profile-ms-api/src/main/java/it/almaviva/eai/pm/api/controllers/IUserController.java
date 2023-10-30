package it.almaviva.eai.pm.api.controllers;

import io.swagger.annotations.*;
import it.almaviva.eai.pm.api.data.ActionData;
import it.almaviva.eai.pm.shared.dto.domain.DomainDTO;
import it.almaviva.eai.pm.shared.dto.domainvalue.DomainvalueDTO;
import it.almaviva.eai.pm.shared.dto.group.GroupDTO;
import it.almaviva.eai.pm.shared.dto.microservice.MicroserviceDTO;
import it.almaviva.eai.pm.shared.dto.microservice.ProfileDTO;
import it.almaviva.eai.pm.shared.dto.role.RoleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@Api(value = "user-controller", authorizations =  {@Authorization(value = "X-Auth-Token")})
public interface IUserController {

  @ApiOperation(value = "Return a microservice",  nickname = "getMicroservice", response = MicroserviceDTO.class)
  @GetMapping(value = "/microservices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Return Microservice by UUID" )})
  @ResponseStatus(HttpStatus.OK)
  MicroserviceDTO getMicroservice(@PathVariable("id") @ApiParam(required = true) UUID microserviceID);

  @ApiOperation(value = "Return User's Profile", nickname = "getProfileOfUserForMicroservice",  response = ProfileDTO.class)
  @GetMapping(value = "/microservices/{id}/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Return ProfileDTO from Microservice" )})
  @ResponseStatus(HttpStatus.OK)
  ProfileDTO getProfileOfUserForMicroservice(@PathVariable("id")UUID id,
      @ApiParam(name = "wso2roles", value = "List of roles in WSO", required = false) @RequestParam(required = false)  List<String> wso2roles);

  @ApiOperation(value = "Return all Microservices", nickname = "getMicroservices", response = MicroserviceDTO.class, responseContainer = "List")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid list of Microservices is retrieved" )})
  @GetMapping(value = "/microservices", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<MicroserviceDTO> getMicroservices();

  @ApiOperation(value = "Return a Role", nickname = "getRole",  response = RoleDTO.class)
  @GetMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Role is retrieved" )})
  @ResponseStatus(HttpStatus.OK)
  RoleDTO getRole(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Return all Roles of a Microservice", nickname = "getRolesOfMicroservice", response = RoleDTO.class, responseContainer = "List")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Roles are retrieved" )})
  @GetMapping(value = "/microservices/{id}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<RoleDTO> getRolesOfMicroservice(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Return all Roles of a Microservice", nickname = "getAllRoles", response = RoleDTO.class, responseContainer = "List")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Roles are retrieved" )})
  @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<RoleDTO> getAllRoles();

  @ApiOperation(value = "Return a Group", nickname = "getGroup",response = GroupDTO.class)
  @GetMapping(value = "/groups/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Group is retrieved" )})
  @ResponseStatus(HttpStatus.OK)
  GroupDTO getGroup(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Return all Group of a Microservice", nickname = "getGroupsOfMicroservice", response = GroupDTO.class, responseContainer = "List" )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Groups are retrieved" )})
  @GetMapping(value = "/microservices/{id}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<GroupDTO> getGroupsOfMicroservice(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Return all Groups", nickname = "getAllGroups", response = GroupDTO.class, responseContainer = "List" )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Groups are retrieved" )})
  @GetMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<GroupDTO> getAllGroups();

  @ApiOperation(value = "Retrieve domain",  nickname = "getDomain",response = DomainDTO.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid domain is retrieved")})
  @GetMapping(value = "/domains/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  DomainDTO getDomain(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Retrieve all domains",  nickname = "getDomains", response = DomainDTO.class, responseContainer = "List")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid domains are retrieved")})
  @GetMapping(value = "/domains", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<DomainDTO> getDomains();

  @ApiOperation(value = "Retrieve all domainvalues belongs to Group", nickname = "getDomainvaluesOfGroup", response = DomainvalueDTO.class, responseContainer = "List")
  @GetMapping(value = "/groups/{id}/domainvalues", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Domain values are retrieved" )})
  @ResponseStatus(HttpStatus.OK)
  List<DomainvalueDTO> getDomainvaluesOfGroup(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Retrieve all micorservices belongs to Group", nickname = "getMicroserviceOfGroup", response = MicroserviceDTO.class, responseContainer = "List")
  @GetMapping(value = "/groups/{id}/microservices", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Microservice are retrieved" )})
  @ResponseStatus(HttpStatus.OK)
  List<MicroserviceDTO> getMicroserviceOfGroup(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Retrieve all micorservices belonging to Role", nickname = "getMicroserviceOfRole", response = MicroserviceDTO.class, responseContainer = "List")
  @GetMapping(value = "/roles/{id}/microservices", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Microservice are retrieved" )})
  @ResponseStatus(HttpStatus.OK)
  List<MicroserviceDTO> getMicroserviceOfRole(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Retrieve all domain values belonging to a Domain", nickname = "getDomainvaluesOfDomain",response = DomainvalueDTO.class, responseContainer = "List")
  @GetMapping(value = "/domains/{id}/domainvalues", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Domain Values are retrieved" )})
  @ResponseStatus(HttpStatus.OK)
  List<DomainvalueDTO> getDomainvaluesOfDomain(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Return all Actions of a Microservice", nickname = "getActionsOfMicroservice", response = ActionData.class, responseContainer = "List" )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Actions are retrieved" )})
  @GetMapping(value = "/microservices/{id}/actions", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<ActionData> getActionsOfMicroservice(@PathVariable("id") @ApiParam(required = true) UUID uuid);

  @ApiOperation(value = "Retrieve all actions values belonging to a Role", nickname = "getActionsOfRole",response = ActionData.class, responseContainer = "List")
  @GetMapping(value = "/roles/{id}/actions", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid Actions Values are retrieved" )})
  @ResponseStatus(HttpStatus.OK)
  List<ActionData> getActionsOfRole(@PathVariable("id") @ApiParam(required = true)UUID roleId);

}
