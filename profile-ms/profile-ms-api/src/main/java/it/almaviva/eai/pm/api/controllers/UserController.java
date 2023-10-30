package it.almaviva.eai.pm.api.controllers;

import it.almaviva.eai.ljsa.sdk.core.security.LjsaUser;
import it.almaviva.eai.pm.api.data.ActionData;
import it.almaviva.eai.pm.api.mapper.IActionDataMapper;
import it.almaviva.eai.pm.core.service.IProfileService;
import it.almaviva.eai.pm.shared.dto.action.ActionDTO;
import it.almaviva.eai.pm.shared.dto.domain.DomainDTO;
import it.almaviva.eai.pm.shared.dto.domainvalue.DomainvalueDTO;
import it.almaviva.eai.pm.shared.dto.group.GroupDTO;
import it.almaviva.eai.pm.shared.dto.microservice.MicroserviceDTO;
import it.almaviva.eai.pm.shared.dto.microservice.ProfileDTO;
import it.almaviva.eai.pm.shared.dto.role.RoleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class UserController implements IUserController{

	@Autowired
	private IProfileService iProfileService;

	@Autowired
	private IActionDataMapper iActionDataMapper;

	@Override
	public MicroserviceDTO getMicroservice(UUID microserviceID) {
		MicroserviceDTO microserviceById = iProfileService
				.getMicroserviceById(microserviceID);

		return  microserviceById;
	}

	@Override
	public ProfileDTO getProfileOfUserForMicroservice(UUID microserviceId,  List<String> wso2roles) {
		LjsaUser userDetails = (LjsaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(wso2roles == null || wso2roles.isEmpty()) {
			wso2roles = new ArrayList<>();
			if (userDetails.getClaims() != null && userDetails.getClaims().get("groups") != null) {
				wso2roles = new ArrayList<String>(Arrays.asList((String[]) userDetails.getClaims().get("groups")));
			}
		}
		return iProfileService.getProfile(microserviceId, wso2roles);
	}


	@Override
	public List<MicroserviceDTO> getMicroservices() {
		return iProfileService.getMicroservices();
	}

	@Override
	public RoleDTO getRole(UUID roleId) {
		return iProfileService.getRoleById(roleId);
	}

	@Override
	public List<RoleDTO> getRolesOfMicroservice(UUID microserviceId) {
		return iProfileService.getRoles(microserviceId);
	}


	@Override
	public GroupDTO getGroup(UUID groupId) {
		return iProfileService.getGroupById(groupId);
	}

	@Override
	public List<GroupDTO> getGroupsOfMicroservice(UUID microserviceId) {
		return iProfileService.getGroupsByMicroserviceId(microserviceId);
	}



	@Override
	public DomainDTO getDomain(UUID domainId) {
		return iProfileService.getDomainById(domainId);
	}

	@Override
	public List<DomainDTO> getDomains() {
		return iProfileService.getDomains();
	}

	@Override
	public List<DomainvalueDTO> getDomainvaluesOfGroup(UUID groupId) {
		return iProfileService.getDomainvaluesByGroupId(groupId);
	}

	@Override
	public List<MicroserviceDTO> getMicroserviceOfGroup(UUID uuid) {
		return iProfileService.getMicroservicesByGroupId(uuid);
	}

	@Override
	public List<MicroserviceDTO> getMicroserviceOfRole(UUID uuid) {
		return iProfileService.getMicroservicesByRoleId(uuid);
	}

	@Override
	public List<DomainvalueDTO> getDomainvaluesOfDomain(UUID domainId) {
		return iProfileService.getDomainvaluesByDomainId(domainId);
	}

	@Override
	public List<ActionData> getActionsOfMicroservice(UUID uuid) {
		List<ActionDTO> actionDTOList = iProfileService.getActionsOfMicroservice(uuid);
		return iActionDataMapper.fromDtoIterable(actionDTOList);
	}

	@Override
	public List<GroupDTO> getAllGroups() {
		return iProfileService.getGroups();
	}

	@Override
	public List<RoleDTO> getAllRoles() {
		return iProfileService.getRoles();
	}


	@Override
	public List<ActionData> getActionsOfRole(UUID roleId) {
			List<ActionDTO> actionDTOList = iProfileService.getActionsOfRole(roleId);
			return iActionDataMapper.fromDtoIterable(actionDTOList);
	}

}
