package it.almaviva.eai.pm.core.mapper;

import it.almaviva.eai.pm.integration.entity.ActionEntity;
import it.almaviva.eai.pm.shared.dto.action.ActionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IActionEntityMapper {

  ActionEntity fromDto(ActionDTO actionDTO);

  ActionDTO fromData(ActionEntity actionEntity);

  List<ActionDTO> fromDataIterable(Iterable<ActionEntity> entities);

  List<ActionEntity> fromDtoIterable(Iterable<ActionDTO> dtos);

}
