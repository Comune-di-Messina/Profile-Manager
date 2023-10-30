package it.almaviva.eai.pm.core.mapper;

import it.almaviva.eai.pm.core.grpc.Group;
import it.almaviva.eai.pm.core.grpc.Profile;
import it.almaviva.eai.pm.core.grpc.Role;
import it.almaviva.eai.pm.integration.entity.DomainEntity;
import it.almaviva.eai.pm.integration.entity.GroupEntity;
import it.almaviva.eai.pm.shared.dto.action.ActionDTO;
import it.almaviva.eai.pm.shared.dto.domain.DomainDTO;
import it.almaviva.eai.pm.shared.dto.domainvalue.DomainvalueDTO;
import it.almaviva.eai.pm.shared.dto.group.GroupDTO;
import it.almaviva.eai.pm.shared.dto.microservice.ProfileDTO;
import it.almaviva.eai.pm.shared.dto.role.RoleDTO;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IProfileMapper {

  List<GroupDTO> fromGroupIterable(Iterable<GroupEntity> groupEntities);

  GroupDTO fromGroup(GroupEntity groupEntity);

 // List<DomainvalueDTO> fromDomainvalueIterable(Iterable<DomainvalueEntity> domainvalueEntityIterable);

 // DomainvalueDTO fromDomainvalue(DomainvalueEntity domainvalueEntity);

  @Mapping(target = "domainvalues" ,ignore = true)
  DomainDTO fromDomain(DomainEntity domainEntity);

  ////////////// protobuf //////////////
  @Mapping(source = "roles", target = "rolesList")
  @Mapping(source = "groups", target = "groupsList")
  Profile map (ProfileDTO profileDTO);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "wso2name", target = "wso2Name")
  @Mapping(source = "actions", target = "actionsList")
  Role map (RoleDTO roleDTO);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "description", target = "description")
  Role.Action map (ActionDTO actionDTO);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "wso2name", target = "wso2Name")
  @Mapping(source = "domainvalues", target = "domainvaluesList")
  Group map (GroupDTO groupDTO);

  @Mapping(source = "domain", target = "domain")
  @Mapping(source = "value", target = "value")
  @Mapping(source = "description", target = "description")
  Group.DomainValue map(DomainvalueDTO domainvalueDTO);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "description", target = "description")
  @Mapping(source = "wso2name", target = "wso2Name")
  Group.DomainValue.Domain map (DomainDTO domainDTO);


}
