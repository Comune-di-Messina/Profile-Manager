package it.almaviva.eai.um.api;

import it.almaviva.eai.um.common.dto.KOMessageDTO;
import it.almaviva.eai.um.common.dto.ResponseDTO;
import it.almaviva.eai.um.common.dto.role.RoleDTO;
import it.almaviva.eai.um.common.dto.role.RoleUpdateDTO;
import it.almaviva.eai.um.common.dto.role.bulk.*;
import it.almaviva.eai.um.common.dto.user.UserDTO;
import it.almaviva.eai.um.common.dto.user.bulk.UserCreateBulkDTO;
import it.almaviva.eai.um.common.dto.user.bulk.UserDeleteBulkDTO;
import it.almaviva.eai.um.service.IAuthorizationService;
import it.almaviva.eai.um.service.IRoleService;
import it.almaviva.eai.um.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class BulkAPI implements IBulkApi {

  @Autowired
  private IUserService iUserService;

  @Autowired
  private IRoleService iRoleService;

  @Autowired
  private IAuthorizationService iAuthorizationService;


  @Override
  public ResponseEntity<ResponseDTO> bulkCreateRoles(RoleCreateBulkDTO roleCreateBulkDTO) {
    List<KOMessageDTO> koList = new ArrayList<>();
    for (RoleDTO roleDTO : roleCreateBulkDTO.getNewRoles()) {
      try {
        iRoleService.createRole(roleDTO);
      } catch (Exception e) {
        e.printStackTrace();
        koList.add(KOMessageDTO.builder().id(roleDTO.getId()).message(e.getMessage()).build());
      }
    }

    return ResponseEntity.status(koList.size() == 0 ? HttpStatus.OK : HttpStatus.CONFLICT)
        .body(ResponseDTO.builder().ko(koList).build());
  }

  @Override
  public ResponseEntity<ResponseDTO> bulkDeleteRoles(RoleDeleteBulkDTO roleDeleteBulkDTO) {
    List<KOMessageDTO> koList = new ArrayList<>();
    for (String s : roleDeleteBulkDTO.getDeletedRoles()) {
      try {
        iRoleService.deleteRole(s);
      } catch (Exception e) {
        e.printStackTrace();
        koList.add(KOMessageDTO.builder().id(s).message(e.getMessage()).build());
      }
    }

    return ResponseEntity.status(koList.size() == 0 ? HttpStatus.OK : HttpStatus.CONFLICT)
        .body(ResponseDTO.builder().ko(koList).build());
  }

  @Override
  public ResponseEntity<ResponseDTO> bulkDeleteUsers(UserDeleteBulkDTO userDeleteBulkDTO) {
    List<KOMessageDTO> koList = new ArrayList<>();
    for (String s : userDeleteBulkDTO.getDeletedUsers()) {
      try {
        iUserService.deleteUser(s);
      } catch (Exception e) {
        e.printStackTrace();
        koList.add(KOMessageDTO.builder().id(s).message(e.getMessage()).build());
      }
    }

    return ResponseEntity.status(koList.size() == 0 ? HttpStatus.OK : HttpStatus.CONFLICT)
        .body(ResponseDTO.builder().ko(koList).build());
  }


  @Override
  public ResponseEntity<ResponseDTO> bulkCreateUsers(UserCreateBulkDTO userCreateBulkDTO) {
    List<KOMessageDTO> koList = new ArrayList<>();
    for (UserDTO userDTO : userCreateBulkDTO.getNewUsers()) {
      try {
        iUserService.createUser(userDTO);
      } catch (Exception e) {
        e.printStackTrace();
        koList.add(KOMessageDTO.builder().id(userDTO.getUsername()).message(e.getMessage()).build());
      }
    }

    return ResponseEntity.status(koList.size() == 0 ? HttpStatus.OK : HttpStatus.CONFLICT)
        .body(ResponseDTO.builder().ko(koList).build());
  }

  @Override
  public ResponseEntity<ResponseDTO> bulkUpdateRoles(RoleUpdateBulkDTO roleUpdateBulkDTOS) {

    List<KOMessageDTO> koList = new ArrayList<>();
    for (RoleItemUpdateBulkDTO item : roleUpdateBulkDTOS.getUpdatedRoles()) {
      try {
        String rolename = item.getId();
        iRoleService.updateRole(rolename, RoleUpdateDTO.builder().deletedUsers(item.getDeletedUsers()).newUsers(item.getNewUsers()).build());
      } catch (Exception e) {
        e.printStackTrace();
        koList.add(KOMessageDTO.builder().id(item.getId()).message(e.getMessage()).build());
      }
    }

    return ResponseEntity.status(koList.size() == 0 ? HttpStatus.OK : HttpStatus.CONFLICT)
        .body(ResponseDTO.builder().ko(koList).build());
  }

  @Override
  public ResponseEntity<ResponseDTO> bulkUpdatePermissions(RolePermissionBulkDTO rolePermissionBulkDTO) {

    List<KOMessageDTO> koList = new ArrayList<>();
    for (RolePermissionItemBulkDTO item : rolePermissionBulkDTO.getUpdatedRoles()) {

        String rolename = item.getId();

        for(String permissionId : item.getAuthorizePermissions()) {
          try {
            iAuthorizationService.authorizeRole(rolename , permissionId);
          } catch (Exception e) {
            e.printStackTrace();
            koList.add(KOMessageDTO.builder().id(rolename+"-"+permissionId).message(e.getMessage()).build());
          }
        }

      for(String permissionId : item.getDenyPermissions()) {
        try {
          iAuthorizationService.denyRole(rolename, permissionId);
        } catch (Exception e) {
          e.printStackTrace();
          koList.add(KOMessageDTO.builder().id(rolename+"-"+permissionId).message(e.getMessage()).build());
        }
      }
    }

    return ResponseEntity.status(koList.size() == 0 ? HttpStatus.OK : HttpStatus.CONFLICT)
        .body(ResponseDTO.builder().ko(koList).build());
  }
}
