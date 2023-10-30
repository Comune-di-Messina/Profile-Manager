package it.almaviva.eai.um.api;

import io.swagger.annotations.*;
import it.almaviva.eai.um.common.dto.role.RoleDTO;
import it.almaviva.eai.um.common.dto.role.RoleRenameDTO;
import it.almaviva.eai.um.common.dto.role.RoleUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(value = "Role API", authorizations =  {@Authorization(value = "X-Auth-Token")})
public interface IRoleApi {
    @ApiOperation(value = "Get a list of all the roles created in the system", nickname = "getRoles",  response = String.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Valid Roles are found", response = String[].class),
        @ApiResponse(code = 404, message = "Valid Roles are not found") })
    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<String> getRoles();

    @ApiOperation(value = "Create new Role",  nickname = "createRole")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Valid Role is created"),
        @ApiResponse(code = 404, message = "Role is not found") })
    @PostMapping(value="/admin/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Valid Role is created")
    void createRole(@RequestBody@ApiParam(required = true) RoleDTO roleDTO);

    @ApiOperation(value = "Update Role", nickname = "updateRole")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Valid Role is created"),
        @ApiResponse(code = 404, message = "Role is not found") })
    @PutMapping(value="/admin/roles/_update",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void updateRole(@RequestParam("rolename")@ApiParam(required = true) String rolename, @RequestBody @ApiParam(required = true)RoleUpdateDTO roleUpdateDTO);

    @ApiOperation(value = "Rename Role",  nickname = "renameRole")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Valid Role is created"),
        @ApiResponse(code = 404, message = "Role is not found") })
    @PutMapping(value="/admin/roles/_rename",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void renameRole(@RequestParam("rolename")@ApiParam(required = true) String rolename, @ApiParam(required = true)@RequestBody RoleRenameDTO roleRenameDTO);

    @ApiOperation(value = "Retrieve a list of all users belonging to a role", nickname = "getUsersOfRole", response = String.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Valid Role are found",  response = String.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Valid Role are not found") })
    @GetMapping(value = "/roles/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<String> getUsersOfRole(@RequestParam("rolename")@ApiParam(required = true) String rolename);

    @ApiOperation(value = "Delete a given role name", nickname = "deleteRole")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Role is deleted"),
        @ApiResponse(code = 404, message = "Valid Role is not found") })
    @DeleteMapping(value = "/admin/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRole(@RequestParam("rolename")@ApiParam(required = true) String rolename);

}
