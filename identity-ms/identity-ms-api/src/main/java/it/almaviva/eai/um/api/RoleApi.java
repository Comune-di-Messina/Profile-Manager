package it.almaviva.eai.um.api;

import it.almaviva.eai.um.common.dto.role.RoleDTO;
import it.almaviva.eai.um.common.dto.role.RoleRenameDTO;
import it.almaviva.eai.um.common.dto.role.RoleUpdateDTO;
import it.almaviva.eai.um.service.IRoleService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class RoleApi implements IRoleApi {

    @Autowired
    private IRoleService iRoleService;

    @Override
    public List<String> getRoles() {
        List<String> allRoles = iRoleService.getAllRoles();
        allRoles.removeIf(s -> s.startsWith("Application/"));
        return allRoles;

    }

    @SneakyThrows
    @Override
    public void createRole(RoleDTO roleDTO) {
        iRoleService.createRole(roleDTO);
    }

    @Override
    public void updateRole(String roleId, RoleUpdateDTO roleUpdateDTO) {
        iRoleService.updateRole(roleId, roleUpdateDTO);
    }

    @Override
    public void renameRole(String roleId, RoleRenameDTO roleRenameDTO) {
        iRoleService.renameRole(roleId, roleRenameDTO);
    }


    @Override
    public List<String> getUsersOfRole(String roleId) {
        return iRoleService.getUserListOfRole(roleId);
    }

    @SneakyThrows
    @Override
    public void deleteRole(String roleId) {
        iRoleService.deleteRole(roleId);
    }


}
