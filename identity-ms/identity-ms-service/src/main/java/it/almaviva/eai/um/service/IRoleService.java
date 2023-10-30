package it.almaviva.eai.um.service;

import it.almaviva.eai.um.common.dto.role.RoleDTO;
import it.almaviva.eai.um.common.dto.role.RoleRenameDTO;
import it.almaviva.eai.um.common.dto.role.RoleUpdateDTO;

import java.util.List;

public interface IRoleService {

    List<String> getAllRoles();

    void createRole(RoleDTO roleBody) throws Exception;

    List<String> getUserListOfRole(String roleId);

    void deleteRole(String roleId) throws Exception;

    void updateRole(String roleId, RoleUpdateDTO roleDTO);

    void renameRole(String roleId, RoleRenameDTO roleDTO);

}
