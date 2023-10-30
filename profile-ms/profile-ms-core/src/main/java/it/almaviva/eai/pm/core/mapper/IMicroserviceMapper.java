package it.almaviva.eai.pm.core.mapper;

import it.almaviva.eai.pm.integration.entity.ActionEntity;
import it.almaviva.eai.pm.integration.entity.GroupEntity;
import it.almaviva.eai.pm.integration.entity.MicroserviceEntity;
import it.almaviva.eai.pm.integration.entity.RoleEntity;
import it.almaviva.eai.pm.shared.dto.action.ActionDTO;
import it.almaviva.eai.pm.shared.dto.group.GroupDTO;
import it.almaviva.eai.pm.shared.dto.microservice.MicroserviceDTO;
import it.almaviva.eai.pm.shared.dto.role.RoleDTO;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IMicroserviceMapper {

  // ignora i campi lazy
  @IterableMapping(qualifiedByName = "fromMicroservice")
  List<MicroserviceDTO> fromMicroserviceIterable(Iterable<MicroserviceEntity> microserviceEntityList);

  // ignora i campi lazy
  @Named("fromMicroservice")
  @Mapping(target = "groups", ignore = true)
  @Mapping(target = "roles", ignore = true)
  @Mapping(target = "actions", ignore = true)
  MicroserviceDTO fromMicroservice(MicroserviceEntity microserviceEntity);

  @Named("fromMicroserviceWithMembership")
  @Mapping(target = "groups", qualifiedByName = "fromGroupsOfMicroservice")
  @Mapping(target = "roles", qualifiedByName = "fromRolesOfMicroservice")
  MicroserviceDTO fromMicroserviceWithMembership(MicroserviceEntity microserviceEntity);

  //------------ group
  @Named("fromGroupsOfMicroservice")
  @Mappings({
      @Mapping(target = "domainvalues" ,ignore = true)
  })
  GroupDTO fromGroup(GroupEntity groupEntity);

  //-- ROLE
  @Named("fromRolesOfMicroservice")
  RoleDTO fromRole(RoleEntity roleEntity);

  ActionDTO fromAction(ActionEntity actionEntity);

}
