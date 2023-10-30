package it.almaviva.eai.um.api;

import io.swagger.annotations.*;
import it.almaviva.eai.um.common.dto.ResponseDTO;
import it.almaviva.eai.um.common.dto.role.bulk.RoleCreateBulkDTO;
import it.almaviva.eai.um.common.dto.role.bulk.RoleDeleteBulkDTO;
import it.almaviva.eai.um.common.dto.role.bulk.RolePermissionBulkDTO;
import it.almaviva.eai.um.common.dto.role.bulk.RoleUpdateBulkDTO;
import it.almaviva.eai.um.common.dto.user.bulk.UserCreateBulkDTO;
import it.almaviva.eai.um.common.dto.user.bulk.UserDeleteBulkDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Bulk API", authorizations =  {@Authorization(value = "X-Auth-Token")})
@RequestMapping("/admin/bulk")
public interface IBulkApi {

  @ApiOperation(value = "Create roles in bulk mode", nickname = "bulkCreateRoles")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid roles are created", response = ResponseDTO.class), @ApiResponse(code = 409, message = "errors", response = ResponseDTO.class)})
  @PostMapping(value="/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ResponseDTO> bulkCreateRoles(@RequestBody @ApiParam(required = true)  RoleCreateBulkDTO roleCreateBulkDTO);


  @ApiOperation(value = "Delete roles in bulk mode", nickname = "bulkDeleteRoles")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Roles are deleted", response = ResponseDTO.class), @ApiResponse(code = 409, message = "errors", response = ResponseDTO.class)})
  @DeleteMapping(value="/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ResponseDTO> bulkDeleteRoles(@RequestBody @ApiParam(required = true)  RoleDeleteBulkDTO roleDeleteBulkDTO);

  @ApiOperation(value = "Delete users in bulk mode", nickname = "bulkDeleteUsers")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Users are deleted", response = ResponseDTO.class), @ApiResponse(code = 409, message = "errors", response = ResponseDTO.class)})
  @DeleteMapping(value="/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ResponseDTO> bulkDeleteUsers(@RequestBody @ApiParam(required = true) UserDeleteBulkDTO userDeleteBulkDTO);

  @ApiOperation(value = "Create users in bulk mode", nickname = "bulkCreateUsers")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid users are created", response = ResponseDTO.class), @ApiResponse(code = 409, message = "errors", response = ResponseDTO.class)})
  @PostMapping(value="/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ResponseDTO> bulkCreateUsers(@RequestBody  @ApiParam(required = true) UserCreateBulkDTO userCreateBulkDTO);

  @ApiOperation(value = "Update roles in bulk mode", nickname = "bulkUpdateRoles")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid user is created", response = ResponseDTO.class), @ApiResponse(code = 409, message = "errors", response = ResponseDTO.class)})
  @PatchMapping(value="/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ResponseDTO>  bulkUpdateRoles(@RequestBody @ApiParam(required = true) RoleUpdateBulkDTO roleUpdateBulkDTOS);

  @ApiOperation(value = "Update permissions in bulk mode", nickname = "bulkUpdatePermissions")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Valid user is created", response = ResponseDTO.class), @ApiResponse(code = 409, message = "errors", response = ResponseDTO.class)})
  @PatchMapping(value="/permissions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ResponseDTO> bulkUpdatePermissions(@RequestBody @ApiParam(required = true) RolePermissionBulkDTO rolePermissionBulkDTO);

}
