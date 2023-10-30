package it.almaviva.eai.pm.api.mapper;

import it.almaviva.eai.pm.api.data.ActionData;
import it.almaviva.eai.pm.shared.dto.action.ActionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IActionDataMapper {

  @Mapping(source = "description", target = "desc")
  ActionData fromDto(ActionDTO actionDTO);

  @Mapping(source = "desc", target = "description")
  ActionDTO fromData(ActionData actionData);

  List<ActionDTO> fromDataIterable(Iterable<ActionData> datas);

  List<ActionData> fromDtoIterable(Iterable<ActionDTO> dtos);

}
