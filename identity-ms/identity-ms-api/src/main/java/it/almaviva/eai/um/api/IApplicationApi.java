package it.almaviva.eai.um.api;

import io.swagger.annotations.*;
import it.almaviva.eai.um.common.dto.role.RoleUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Application API", authorizations =  {@Authorization(value = "X-Auth-Token")})
public interface IApplicationApi {

  @ApiOperation(value = "Return applications according to the filter", nickname = "getApplications", notes = "Returns HTTP 404 if the Applications are not found", response = String.class, responseContainer ="List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Valid Applications are found", response = String.class, responseContainer ="List"),
      @ApiResponse(code = 404, message = "Valid Applications are not found") })
  @GetMapping(value = "/applications", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<String> getApplications();

  @ApiOperation(value = "Return Users belongs to some Application", nickname = "getUsersOfApplications", notes = "Returns HTTP 404 if the Users are not found", response = String.class, responseContainer ="List")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Valid Users are found", response = String.class, responseContainer ="List"),
      @ApiResponse(code = 404, message = "Valid Users are not found") })
  @GetMapping(value = "/applications/users", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<String> getUsersOfApplications(@RequestParam("id") @ApiParam(required = true)String applicationId);

  @ApiOperation(value = "Update Application",  nickname = "updateApplication",  notes = "Returns HTTP 201 if the Application is successfully updated")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Valid Application"),
      @ApiResponse(code = 404, message = "Application is not found") })
  @PutMapping(value="/admin/applications",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  void updateApplication(@RequestParam("id") @ApiParam(required = true) String applicationId, @ApiParam(required = true)@RequestBody RoleUpdateDTO roleUpdateDTO);

}
