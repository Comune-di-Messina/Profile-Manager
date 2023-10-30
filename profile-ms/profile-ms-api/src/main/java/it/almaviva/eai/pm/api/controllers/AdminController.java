package it.almaviva.eai.pm.api.controllers;

import it.almaviva.eai.pm.api.data.ActionData;
import it.almaviva.eai.pm.api.mapper.IActionDataMapper;
import it.almaviva.eai.pm.core.service.IProfileService;
import it.almaviva.eai.pm.shared.dto.action.ActionDTO;
import it.almaviva.eai.pm.shared.dto.domain.CreateDomainDTO;
import it.almaviva.eai.pm.shared.dto.domainvalue.CreateDomainvalueDTO;
import it.almaviva.eai.pm.shared.dto.group.CreateGroupDTO;
import it.almaviva.eai.pm.shared.dto.microservice.CreateMicroserviceDTO;
import it.almaviva.eai.pm.shared.dto.role.CreateRoleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class AdminController implements IAdminController{

	@Autowired
	private IProfileService iProfileService;

	@Autowired
	private IActionDataMapper iActionDataMapper;

	@Override
	public UUID insertDomain(CreateDomainDTO createDomainDTO){
	  return iProfileService.insertDomain(createDomainDTO);
	}

  @Override
  public void updateDomain(UUID domainId, @Valid CreateDomainDTO createDomainDTO) {
		iProfileService.updateDomain(domainId, createDomainDTO);
  }

  @Override
  public void deleteDomain(UUID uuid){
		iProfileService.deleteDomainById(uuid);
  }

	@Override
	public UUID insertDomainvalue(UUID domainID, CreateDomainvalueDTO createDomainvalueDTO) {
		return iProfileService.addDomainValueToDomain(domainID, createDomainvalueDTO);
	}


	@Override
	public void deleteDomainvalue(UUID domainvalueID) {
		iProfileService.deleteDomainvalueById(domainvalueID);
	}

	@Override
	public UUID insertRole(UUID id, CreateRoleDTO createRoleDTO) {
		return iProfileService.insertRole(id,createRoleDTO);
	}

	@Override
	public UUID insertGroup(UUID id, CreateGroupDTO createGroupDTO) {
		return iProfileService.insertGroup(id, createGroupDTO);
	}

	@Override
	public UUID insertMicroservice(CreateMicroserviceDTO createMicroserviceDTO) {
		return iProfileService.insertMicroservice(createMicroserviceDTO);
	}

	@Override
	public void deleteMicroservice(UUID microserviceID){
		iProfileService.deleteMicroserviceById(microserviceID);
	}

	@Override
	public void deleteRole(UUID roleId)  {
		iProfileService.deleteRoleById(roleId);
	}

	@Override
	public void deleteGroup(UUID groupId)  {
		iProfileService.deleteGroupById(groupId);
	}

	@Override
	public void addDomainvalueToGroup(UUID groupId, UUID domainvalueId) {
		iProfileService.addDomainvalueToGroup(groupId, domainvalueId);
	}

	@Override
	public void removeDomainvalueFromGroup(UUID groupId, UUID domainvalueId) {
		iProfileService.removeDomainvalueFromGroup(groupId, domainvalueId);
	}

	@Override
	public void addMicroserviceToGroup(UUID groupId, UUID microserviceId) {
		iProfileService.addMicroserviceToGroup(groupId, microserviceId);
	}

	@Override
	public void removeMicroserviceFromGroup(UUID groupId, UUID microserviceId) {
		iProfileService.removeMicroserviceFromGroup(groupId, microserviceId);
	}

	@Override
	public void addMicroserviceToRole(UUID roleId, UUID microserviceId) {
		iProfileService.addMicroserviceToRole(roleId, microserviceId);
	}

	@Override
	public void removeMicroserviceFromRole(UUID roleId, UUID microserviceId) {
		iProfileService.removeMicroserviceFromRole(roleId, microserviceId);
	}


	@Override
	public UUID insertAction(UUID microserviceId, ActionData actionData) {
		ActionDTO actionDTO = iActionDataMapper.fromData(actionData);
		return iProfileService.insertAction(microserviceId,actionDTO);
	}


	@Override
	public void deleteAction(UUID id) {
		iProfileService.deleteActionById(id);
	}


	@Override
	public void addActionToRole(List<UUID> actionId, UUID roleId) {
		iProfileService.addActionToRole(actionId, roleId);

	}


	@Override
	public void removeActionFromRole(UUID actionId, UUID roleId) {
		iProfileService.removeActionFromRole(actionId, roleId);
	}

}
