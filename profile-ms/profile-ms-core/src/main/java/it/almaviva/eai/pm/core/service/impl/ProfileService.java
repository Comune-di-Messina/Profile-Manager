package it.almaviva.eai.pm.core.service.impl;

import it.almaviva.eai.pm.core.mapper.*;
import it.almaviva.eai.pm.core.service.IProfileService;
import it.almaviva.eai.pm.integration.entity.*;
import it.almaviva.eai.pm.integration.repository.*;
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
import it.almaviva.eai.pm.shared.throwable.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Service
public class ProfileService implements IProfileService {

    @Autowired
    private IGroupRepository iGroupRepository;

    @Autowired
    private IRoleRepository iRoleRepository;


    @Autowired
    private IDomainRepository iDomainRepository;

    @Autowired
    private IDomainValueRepository iDomainValueRepository;

    @Autowired
    private IMicroserviceRepository iMicroserviceRepository;

    @Autowired
    private IMicroserviceMapper iMicroserviceMapper;

    @Autowired
    private IProfileMapper iProfileMapper;

    @Autowired
    private IDomainMapper iDomainMapper;

    @Autowired
    private IGroupMapper iGroupMapper;

    @Autowired
    private IRoleMapper iRoleMapper;

    @Autowired
    private IDomainvalueMapper iDomainvalueMapper;

  @Autowired
  private IActionRepository iActionRepository;


  @Autowired
  private IActionEntityMapper iActionEntityMapper;


  @Override
  public UUID insertMicroservice(CreateMicroserviceDTO createMicroserviceDTO) {
    MicroserviceEntity microserviceEntity = new MicroserviceEntity();
    microserviceEntity.setName(createMicroserviceDTO.getName());
    microserviceEntity.setDescription(createMicroserviceDTO.getDesc());
    return  iMicroserviceRepository.save(microserviceEntity).getId();
  }

  @Transactional
  @Override
  public UUID insertAction(UUID microserviceId, ActionDTO actionDTO) {
    Optional<MicroserviceEntity> byId = iMicroserviceRepository.findById(microserviceId);
    if(!byId.isPresent()){
      throw new NotFoundException("No Microservice Found with ID "+ microserviceId);
    }

    MicroserviceEntity microserviceEntity = byId.get();
    ActionEntity actionEntity = iActionEntityMapper.fromDto(actionDTO);
    microserviceEntity.addAction(actionEntity);
    return  iActionRepository.save(actionEntity).getId();
  }

  @Override
  public void deleteActionById(UUID id) {
    try {
    iActionRepository.deleteById(id);
  }catch (EmptyResultDataAccessException e){
    log.error("", e);
    throw new NotFoundException("No Domain with id "+ id +" exists");
  }
  }

  @Transactional
  @Override
  public void addActionToRole(List<UUID> actionId, UUID roleId) {
    Optional<RoleEntity> optionalRoleEntity = iRoleRepository.findById(roleId);
    if(!optionalRoleEntity.isPresent()){
      throw new NotFoundException("No role with id "+ roleId +" exists");
    }

    for(UUID uuid : actionId) {

      Optional<ActionEntity> optionalActionEntity = iActionRepository.findById(uuid);
      if (!optionalActionEntity.isPresent()) {
        throw new NotFoundException("No action with id " + actionId + " exists");
      }

      RoleEntity roleEntity = optionalRoleEntity.get();
      ActionEntity actionEntity = optionalActionEntity.get();

      Set<MicroserviceEntity> microservice = roleEntity.getMicroservice();
      final boolean match = microservice.parallelStream().anyMatch(
              microserviceEntity -> microserviceEntity.getId()
                      .equals(actionEntity.getMicroservice().getId()));

      if (!match) {
        throw new RuntimeException("Can not add Action to Role belonging to another microservice");
      }

      roleEntity.addAction(optionalActionEntity.get());
    }
  }

  @Transactional
  @Override
  public void removeActionFromRole(UUID actionId, UUID roleId) {
    Optional<RoleEntity> optionalRoleEntity = iRoleRepository.findById(roleId);
    if(!optionalRoleEntity.isPresent()){
      throw new NotFoundException("No role with id "+ roleId +" exists");
    }

    Optional<ActionEntity> optionalActionEntity = iActionRepository.findById(actionId);
    if(!optionalActionEntity.isPresent()){
      throw new NotFoundException("No action with id "+ actionId +" exists");
    }

    RoleEntity roleEntity = optionalRoleEntity.get();
    roleEntity.removeAction(optionalActionEntity.get());
  }


  @Override
  @Transactional
  public UUID insertRole(UUID id, CreateRoleDTO createRoleDTO) {
    Optional<MicroserviceEntity> byId = iMicroserviceRepository.findById(id);
    if(!byId.isPresent()){
      throw new NotFoundException("No microservice with id "+ id +" exists");
    }

    MicroserviceEntity microserviceEntity = byId.get();
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setName(createRoleDTO.getRoleName());
    roleEntity.setWso2name(createRoleDTO.getWso2Name());
    RoleEntity re = iRoleRepository.save(roleEntity);

    microserviceEntity.addRole(re);

    return re.getId();
  }

  @Transactional
  @Override
  public MicroserviceDTO getMicroserviceById(UUID uuid) {

    Optional<MicroserviceEntity> byId = iMicroserviceRepository.findById(uuid);
    if(!byId.isPresent()){
      throw new NotFoundException("No microservice with id "+ uuid +" exists");
    }

    return iMicroserviceMapper.fromMicroserviceWithMembership(iMicroserviceRepository.findMembershipsByMicroserviceId(uuid));

  }


  @Override
  public void deleteMicroserviceById(UUID uuid) {
    try {
      iMicroserviceRepository.deleteById(uuid);
    } catch (EmptyResultDataAccessException e){
        log.error("", e);
        throw new NotFoundException("No Microservice with id "+ uuid +" exists");
    }
  }

  public UUID insertDomain(CreateDomainDTO createDomainDTO) {
    DomainEntity domain = new DomainEntity();
    domain.setName(createDomainDTO.getName());
    domain.setWso2name(createDomainDTO.getWso2name());
    domain.setDescription(createDomainDTO.getDesc());

    DomainEntity newDomain = iDomainRepository.save(domain);
    return newDomain.getId();
  }

  @Override
  @Transactional
  public UUID addDomainValueToDomain(UUID domainID, CreateDomainvalueDTO createDomainvalueDTO) {

    Optional<DomainEntity> byId = iDomainRepository.findById(domainID);
    if(!byId.isPresent()){
      throw new NotFoundException("No Domain with id "+ domainID +" exists");
    }

    DomainEntity domainEntity = byId.get();

    DomainvalueEntity domainvalueEntity = new DomainvalueEntity();
    domainvalueEntity.setValue(createDomainvalueDTO.getValue());
    domainvalueEntity.setDescription(createDomainvalueDTO.getDesc());
    domainEntity.addDomainvalue(domainvalueEntity);
    return iDomainValueRepository.save(domainvalueEntity).getId();
  }



  @Override
  @Transactional
  public UUID insertGroup(UUID id, CreateGroupDTO createGroupDTO) {
    Optional<MicroserviceEntity> byId = iMicroserviceRepository.findById(id);
    if(!byId.isPresent()){
      throw new NotFoundException("No Microservice Found with ID "+ id);
    }

    MicroserviceEntity microserviceEntity = byId.get();
    GroupEntity groupEntity = new GroupEntity();
    groupEntity.setName(createGroupDTO.getName());
    groupEntity.setWso2name(createGroupDTO.getWso2Name());
    microserviceEntity.addGroup(groupEntity);
    return iGroupRepository.save(groupEntity).getId();
  }

  @Override
  @Transactional
  public void deleteGroupById(UUID groupId) {
    Optional<GroupEntity> optionalGroupEntity = iGroupRepository.findById(groupId);
    if(!optionalGroupEntity.isPresent()){
      throw new NotFoundException("No Group found with ID "+ groupId);
    }

    GroupEntity groupEntity = optionalGroupEntity.get();
    for(DomainvalueEntity domainvalueEntity : groupEntity.getDomainvalues()){
      groupEntity.removeDomainValue(domainvalueEntity);
    }


    for(MicroserviceEntity microserviceEntity : groupEntity.getMicroservices()){
      groupEntity.removeMicroservice(microserviceEntity);
    }

    iGroupRepository.deleteById(groupId);
  }

  @Override
  @Transactional
  public void deleteRoleById(UUID roleId) {
    Optional<RoleEntity> optionalRoleEntity = iRoleRepository.findById(roleId);
    if(!optionalRoleEntity.isPresent()){
      throw new NotFoundException("No Role found with ID "+ roleId);
    }

    RoleEntity roleEntity = optionalRoleEntity.get();
    for(MicroserviceEntity microserviceEntity : roleEntity.getMicroservice()){
      roleEntity.removeMicroservice(microserviceEntity);
    }


    iRoleRepository.deleteById(roleId);
  }

  @Override
  public void deleteDomainvalueById(UUID domainvalueID) {
    iDomainValueRepository.deleteById(domainvalueID);
  }

  @Override
  public void deleteDomainById(UUID domainId) {
    try {
      iDomainRepository.deleteById(domainId);
    }catch (EmptyResultDataAccessException e){
      log.error("", e);
      throw new NotFoundException("No Domain with id "+ domainId +" exists");
    }

  }

  @Override
  public List<MicroserviceDTO> getMicroservices() {
    return iMicroserviceMapper.fromMicroserviceIterable(iMicroserviceRepository.findAll());
  }

  @Override
  public List<MicroserviceDTO> getMicroservicesByGroupId(UUID groupId) {
    Iterable<MicroserviceEntity> microserviceEntityIterable = iMicroserviceRepository.findMicroserviceEntitiesByGroups_Id(groupId);
    return iMicroserviceMapper.fromMicroserviceIterable(microserviceEntityIterable);
  }

  @Override
  public List<MicroserviceDTO> getMicroservicesByRoleId(UUID roleId) {
    Iterable<MicroserviceEntity> microserviceEntityIterable = iMicroserviceRepository.findMicroserviceEntitiesByRoles_Id(roleId);
    return iMicroserviceMapper.fromMicroserviceIterable(microserviceEntityIterable);
  }

  @Transactional
  @Override
  public RoleDTO getRoleById(UUID roleId) {
    Optional<RoleEntity> byId = iRoleRepository.findById(roleId);
    if(!byId.isPresent()){
      throw new NotFoundException("No Group with id "+ roleId +" exists");
    }

    return  iRoleMapper.fromRole(byId.get());
  }

  @Transactional
  @Override
  public List<RoleDTO> getRoles(UUID microserviceId) {
   return iRoleMapper.fromRoleIterable(iRoleRepository.findRoleEntitiesByMicroservice_Id(microserviceId));
  }

  @Override
  public GroupDTO getGroupById(UUID groupId) {
    Optional<GroupEntity> byId = iGroupRepository.findById(groupId);
    if(!byId.isPresent()){
      throw new NotFoundException("No Group with id "+ groupId +" exists");
    }

    return  iGroupMapper.fromGroup(byId.get());
  }

  @Override
  public DomainDTO getDomainById(UUID domainId) {

    DomainEntity domainEntity = iDomainRepository
        .findByIdAndFetchDomainvaluesEagerly(domainId);

    if(domainEntity == null){
      throw new NotFoundException("No Domain with id "+ domainId +" exists");
    }

    return  iDomainMapper.fromDomainWithDomainvalues(domainEntity);
  }

  @Override
  public List<DomainDTO> getDomains() {
    Iterable<DomainEntity> all = iDomainRepository
        .findAll();
    return iDomainMapper.fromDomainIterable(all);
  }

  ///groups/{id}/domainvalues
  @Override
  public List<DomainvalueDTO> getDomainvaluesByGroupId(UUID groupId) {
    Iterable<DomainvalueEntity> allByGroups_id = iDomainValueRepository.findAllByGroups_IdWithDomain(groupId);
    return iDomainvalueMapper.fromDomainvalueIterable(allByGroups_id);
  }

  // /view/microservices/{id}/groups
  @Override
  public List<GroupDTO> getGroupsByMicroserviceId(UUID microserviceId) {
    return iGroupMapper.fromGroupIterable(iGroupRepository.findGroupEntitiesByMicroservices_Id(microserviceId));
  }

  @Override
  @Transactional
  public MicroserviceDTO getMembershipsByMicroserviceId(UUID uuid) {
   return iMicroserviceMapper.fromMicroserviceWithMembership(iMicroserviceRepository.findMembershipsByMicroserviceId(uuid));
  }

  @Override
  public ProfileDTO getProfile(UUID microserviceId, Collection<String> wso2roles) {

    List<RoleDTO> roleDTOS = null;
    List<GroupDTO> groupDTOS = null;
    if(!wso2roles.isEmpty()) {
      Iterable<RoleEntity> rolesEntities = iRoleRepository
          .findRoleEntitiesByMicroservice_IdAndWso2nameIn(microserviceId, wso2roles);
      Iterable<GroupEntity> groupEntities = iGroupRepository
          .findGroupEntitiesByMicroservices_IdAndWso2nameIn(microserviceId, wso2roles);
      groupDTOS = iProfileMapper.fromGroupIterable(groupEntities);
      roleDTOS = iRoleMapper.fromRoleIterable(rolesEntities);
    }

    ProfileDTO profileDTO = new ProfileDTO();
    profileDTO.setRoles(roleDTOS);
    profileDTO.setGroups(groupDTOS);

    return profileDTO;
  }

  @Override
  @Transactional
  public void addDomainvalueToGroup(UUID groupId, UUID domainvalueId) {

    Optional<GroupEntity> optionalGroupEntity = iGroupRepository.findById(groupId);
    if(!optionalGroupEntity.isPresent()){
      throw new NotFoundException("No group with id "+ groupId +" exists");
    }

    Optional<DomainvalueEntity> optionalDomainvalueEntity = iDomainValueRepository.findById(domainvalueId);
    if(!optionalDomainvalueEntity.isPresent()){
      throw new NotFoundException("No domainvalue with id "+ domainvalueId +" exists");
    }

    GroupEntity groupEntity = optionalGroupEntity.get();
    groupEntity.addDomainValue(optionalDomainvalueEntity.get());

  }

  @Override
  @Transactional
  public void removeDomainvalueFromGroup(UUID groupId, UUID domainvalueId) {

    Optional<GroupEntity> optionalGroupEntity = iGroupRepository.findById(groupId);
    if(!optionalGroupEntity.isPresent()){
      throw new NotFoundException("No group with id "+ groupId +" exists");
    }

    Optional<DomainvalueEntity> optionalDomainvalueEntity = iDomainValueRepository.findById(domainvalueId);
    if(!optionalDomainvalueEntity.isPresent()){
      throw new NotFoundException("No domainvalue with id "+ domainvalueId +" exists");
    }

    GroupEntity groupEntity = optionalGroupEntity.get();
    groupEntity.removeDomainValue(optionalDomainvalueEntity.get());
  }

  @Override
  @Transactional
  public void addMicroserviceToGroup(UUID groupId, UUID microserviceId) {

    Optional<GroupEntity> optionalGroupEntity = iGroupRepository.findById(groupId);
    if(!optionalGroupEntity.isPresent()){
      throw new NotFoundException("No group with id "+ groupId +" exists");
    }

    Optional<MicroserviceEntity> optionalMicroserviceEntity = iMicroserviceRepository.findById(microserviceId);
    if(!optionalMicroserviceEntity.isPresent()){
      throw new NotFoundException("No microservice with id "+ microserviceId +" exists");
    }

    GroupEntity groupEntity = optionalGroupEntity.get();
    groupEntity.addMicroservice(optionalMicroserviceEntity.get());
  }

  @Override
  @Transactional
  public void removeMicroserviceFromGroup(UUID groupId, UUID microserviceId) {
    Optional<GroupEntity> optionalGroupEntity = iGroupRepository.findById(groupId);
    if(!optionalGroupEntity.isPresent()){
      throw new NotFoundException("No group with id "+ groupId +" exists");
    }

    Optional<MicroserviceEntity> optionalMicroserviceEntity = iMicroserviceRepository.findById(microserviceId);
    if(!optionalMicroserviceEntity.isPresent()){
      throw new NotFoundException("No microservice with id "+ microserviceId +" exists");
    }

    GroupEntity groupEntity = optionalGroupEntity.get();
    groupEntity.removeMicroservice(optionalMicroserviceEntity.get());
  }

  @Override
  @Transactional
  public void addMicroserviceToRole(UUID roleId, UUID microserviceId) {
    Optional<RoleEntity> optionalRoleEntity = iRoleRepository.findById(roleId);
    if(!optionalRoleEntity.isPresent()){
      throw new NotFoundException("No role with id "+ roleId +" exists");
    }

    Optional<MicroserviceEntity> optionalMicroserviceEntity = iMicroserviceRepository.findById(microserviceId);
    if(!optionalMicroserviceEntity.isPresent()){
      throw new NotFoundException("No microservice with id "+ microserviceId +" exists");
    }

    RoleEntity roleEntity = optionalRoleEntity.get();
    roleEntity.addMicroservice(optionalMicroserviceEntity.get());
  }

  @Override
  @Transactional
  public void removeMicroserviceFromRole(UUID roleId, UUID microserviceId) {
    Optional<RoleEntity> optionalRoleEntity = iRoleRepository.findById(roleId);
    if(!optionalRoleEntity.isPresent()){
      throw new NotFoundException("No group with id "+ roleId +" exists");
    }

    Optional<MicroserviceEntity> optionalMicroserviceEntity = iMicroserviceRepository.findById(microserviceId);
    if(!optionalMicroserviceEntity.isPresent()){
      throw new NotFoundException("No microservice with id "+ microserviceId +" exists");
    }

    RoleEntity roleEntity = optionalRoleEntity.get();
    roleEntity.removeMicroservice(optionalMicroserviceEntity.get());
  }

  @Override
  public void updateDomain(UUID domainId, CreateDomainDTO createDomainDTO) {
    Optional<DomainEntity> byId = iDomainRepository.findById(domainId);
    DomainEntity domainEntity = byId.get();
    domainEntity.setDescription(createDomainDTO.getDesc());
    domainEntity.setName(createDomainDTO.getName());
    domainEntity.setWso2name(createDomainDTO.getWso2name());
    iDomainRepository.save(domainEntity);
  }

  @Override
  public List<DomainvalueDTO> getDomainvaluesByDomainId(UUID domainId) {
    return iDomainvalueMapper.fromDomainvalueIterable(iDomainValueRepository.findAllByDomain_Id(domainId));
  }

  @Override
  public List<GroupDTO> getGroups() {
    return iGroupMapper.fromGroupIterable(iGroupRepository.findAll());
  }

  @Transactional
  @Override
  public List<RoleDTO> getRoles() {
    return iRoleMapper.fromRoleIterable(iRoleRepository.findAll());
  }

  @Override
  public List<ActionDTO> getActions() {
    return iActionEntityMapper.fromDataIterable(iActionRepository.findAll());
  }

  @Override
  public List<ActionDTO> getActionsOfRole(UUID roleId) {
    return iActionEntityMapper.fromDataIterable(iActionRepository.findActionEntitiesByRoles_Id(roleId));

  }

  @Override
  public List<ActionDTO> getActionsOfMicroservice(UUID uuid) {
    Iterable<ActionEntity> actionEntitiesByMicroservice_id = iActionRepository
        .findActionEntitiesByMicroservice_Id(uuid);
    return iActionEntityMapper.fromDataIterable(actionEntitiesByMicroservice_id);

  }


}
