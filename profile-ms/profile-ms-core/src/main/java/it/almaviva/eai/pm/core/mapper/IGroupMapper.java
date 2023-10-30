package it.almaviva.eai.pm.core.mapper;

import it.almaviva.eai.pm.integration.entity.GroupEntity;
import it.almaviva.eai.pm.shared.dto.group.GroupDTO;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IGroupMapper {


  //------------ group
  @Named("fromGroup")
  @Mappings({
      @Mapping(target = "domainvalues" ,ignore = true)

  })
  GroupDTO fromGroup(GroupEntity groupEntity);

  @IterableMapping(qualifiedByName = "fromGroup")
  List<GroupDTO> fromGroupIterable(Iterable<GroupEntity> microserviceEntityList);

}
