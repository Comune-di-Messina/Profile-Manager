package it.almaviva.eai.um.api;

import io.swagger.annotations.*;
import it.almaviva.eai.um.common.dto.user.UserClaimsUpdateDTO;
import it.almaviva.eai.um.common.dto.user.UserDTO;
import it.almaviva.eai.um.common.dto.user.UserUpdateDTO;
import it.almaviva.eai.um.common.dto.user.UserWithClaimsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@Api(value = "User API", authorizations =  {@Authorization(value = "X-Auth-Token")})
public interface IUserApi {

    @ApiOperation(value = "Retrieve a list of all users", nickname = "getUsers", response = UserWithClaimsDTO.class, responseContainer ="List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid users are returned", response = UserWithClaimsDTO.class, responseContainer ="List")} )
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<UserWithClaimsDTO> getUsers(
        @ApiParam(name = "fetch", allowableValues = "eager", value = "Returns users according to the filter with or without claims") @RequestParam(name = "fetch")  Optional<String> fetch,
        @ApiParam(name = "filter", value = "Filter expression for filtering", example = "pete*, *mith") @RequestParam(name = "filter") Optional<String> query,
        @ApiParam(name = "limit", value = "Specifies the desired maximum number of query results",example = "10") @RequestParam(name = "limit") Optional<Integer> limit);

    @ApiOperation(value = "Create new user", nickname = "createUser")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid user is created")})
    @PostMapping(value="/admin/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void createUser(@Valid @RequestBody @ApiParam(required = true) UserDTO userDTO);

    @ApiOperation(value = "Update user", nickname = "updateUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid user is updated")})
    @PutMapping(value="/admin/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void updateUser(@RequestParam("username") @ApiParam(value = "username", required = true) String username,
        @RequestBody @ApiParam(required = true) UserClaimsUpdateDTO userClaimsUpdateDTO) ;

    @ApiOperation(value = "Get the list of roles that a particular user belongs to", nickname = "getRolesOfUser", response = String.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid user is updated", response = String.class, responseContainer = "List")} )
    @GetMapping(value = "/users/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<String> getRolesOfUser(
        @ApiParam(value = "username", required = true) @RequestParam("username") String userId);

    @ApiOperation(value = "Delete a user with the given username", nickname = "deleteUser",notes = "Returns HTTP 204 if the user is successfully deleted.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User is deleted"),
            @ApiResponse(code = 404, message = "Valid user is not found") })
    @DeleteMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(
        @ApiParam(value = "username", required = true) @RequestParam("username") String username);

    @ApiOperation(value = "Assign one or more roles to user" , nickname = "updateRoleListOfUser")
    @PutMapping(value="/admin/users/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void updateRoleListOfUser(
        @ApiParam(value = "username", required = true) @RequestParam("username") String username,
        @RequestBody UserUpdateDTO userUpdateDTO);


    @ApiOperation(value = "Return the applications the user is part of",  nickname = "getApplicationsOfUser", response = String.class, responseContainer ="List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Valid user is updated", response = String.class, responseContainer ="List")} )
    @GetMapping(value = "/users/applications", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<String> getApplicationsOfUser(@ApiParam(value = "Username or userId", required = true) @RequestParam("username") String userId);


    @ApiOperation(value = "Assign one or more applications to user", nickname = "updateApplicationsListOfUser")
    @PutMapping(value="/admin/users/applications", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void updateApplicationsListOfUser(@ApiParam(value = "Username or userId", required = true) @RequestParam("username") String userId, @RequestBody UserUpdateDTO userUpdateDTO);


}
