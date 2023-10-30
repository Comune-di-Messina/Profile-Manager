package it.almaviva.eai.pm.core.mapper;

import it.almaviva.eai.pm.integration.entity.DomainEntity;
import it.almaviva.eai.pm.integration.entity.DomainvalueEntity;
import it.almaviva.eai.pm.shared.dto.domain.DomainDTO;
import it.almaviva.eai.pm.shared.dto.domainvalue.DomainvalueDTO;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IDomainvalueMapper {

  @IterableMapping(qualifiedByName = "fromDomain")
  List<DomainvalueDTO> fromDomainvalueIterable(Iterable<DomainvalueEntity> domainvalueEntityIterable);

  @Mappings({
      @Mapping(target = "domain", ignore = true)
  })
  @Named("fromDomain")
  DomainvalueDTO fromDomainvalue(DomainvalueEntity domainvalueEntity);


  @Mapping(target = "domainvalues", ignore = true)
  DomainDTO fromDomain(DomainEntity domainEntity);

}
