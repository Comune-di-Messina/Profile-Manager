package it.almaviva.eai.pm.core.service;

import it.almaviva.eai.pm.shared.dto.action.ActionDTO;
import it.almaviva.eai.pm.shared.dto.domain.CreateDomainDTO;
import it.almaviva.eai.pm.shared.dto.domain.DomainDTO;
import it.almaviva.eai.pm.shared.dto.domainvalue.CreateDomainvalueDTO;
import it.almaviva.eai.pm.shared.dto.domainvalue.DomainvalueDTO;
import it.almaviva.eai.pm.shared.dto.group.CreateGroupDTO;
import it.almaviva.eai.pm.shared.dto.group.GroupDTO;
import it.almaviva.eai.pm.shared.dto.microservice.CreateMicroserviceDTO;
import it.almaviva.eai.pm.shared.dto.microservice.MicroserviceDTO;
import it.almaviva.eai.pm.shared.dto.microservice.ProfileDTO;
import it.almaviva.eai.pm.shared.dto.role.CreateRoleDTO;
import it.almaviva.eai.pm.shared.dto.role.RoleDTO;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IProfileService {


    MicroserviceDTO getMicroserviceById(UUID microserviceID);

    List<MicroserviceDTO> getMicroservices();

    RoleDTO getRoleById(UUID roleId);

    List<RoleDTO> getRoles(UUID microserviceId);

    GroupDTO getGroupById(UUID groupId);

    DomainDTO getDomainById(UUID domainId);

    List<DomainDTO> getDomains();

    List<DomainvalueDTO> getDomainvaluesByGroupId(UUID groupId);

    UUID insertDomain(CreateDomainDTO createDomainDTO);

    void deleteDomainById(UUID uuid);

    UUID addDomainValueToDomain(UUID domainID, CreateDomainvalueDTO createDomainvalueDTO);

    void deleteDomainvalueById(UUID domainvalueID);

    UUID insertRole(UUID id, CreateRoleDTO createRoleDTO);

    UUID insertGroup(UUID id, CreateGroupDTO createGroupDTO);

    UUID insertMicroservice(CreateMicroserviceDTO createMicroserviceDTO);

    void deleteMicroserviceById(UUID microserviceID);

    void deleteRoleById(UUID roleId);

    void deleteGroupById(UUID groupId);

    List<GroupDTO> getGroupsByMicroserviceId(UUID microserviceId);

    MicroserviceDTO getMembershipsByMicroserviceId(UUID uuid);

    ProfileDTO getProfile(UUID microserviceId, Collection<String> wso2roles);

    void addDomainvalueToGroup(UUID groupId, UUID domainvalueId);

    void removeDomainvalueFromGroup(UUID groupId, UUID domainvalueId);

    void updateDomain(UUID domainId, CreateDomainDTO createDomainDTO);

    List<DomainvalueDTO> getDomainvaluesByDomainId(UUID domainId);

    List<MicroserviceDTO> getMicroservicesByGroupId(UUID groupId);
    List<MicroserviceDTO> getMicroservicesByRoleId(UUID roleId);

    void addMicroserviceToGroup(UUID groupId, UUID microserviceId);

    void removeMicroserviceFromGroup(UUID groupId, UUID microserviceId);

    void addMicroserviceToRole(UUID roleId, UUID microserviceId);

    void removeMicroserviceFromRole(UUID roleId, UUID microserviceId);

    List<GroupDTO> getGroups();

    List<RoleDTO> getRoles();

    List<ActionDTO> getActions();

    List<ActionDTO> getActionsOfRole(UUID roleId);

    UUID insertAction(UUID microserviceId, ActionDTO actionDTO);

    void deleteActionById(UUID id);

    void addActionToRole(List<UUID> actionId, UUID roleId);

    void removeActionFromRole(UUID actionId, UUID roleId);


    List<ActionDTO> getActionsOfMicroservice(UUID uuid);
}
