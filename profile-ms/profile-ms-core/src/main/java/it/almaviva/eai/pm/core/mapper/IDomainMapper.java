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
public interface IDomainMapper {

  @Named("fromDomain")
  @Mapping(target = "domainvalues" ,ignore = true)
  DomainDTO fromDomain(DomainEntity domainEntity);

  //------- Domain

  @Named("fromDomainWithDomainvalues")
  DomainDTO fromDomainWithDomainvalues(DomainEntity domainEntity);


  @Mapping(target = "domain", ignore = true)
  DomainvalueDTO fromDomainvalue(DomainvalueEntity domainvalueEntity);

  List<DomainvalueDTO> fromDomainvalueIterable(Iterable<DomainvalueEntity> domainvalueEntity);

  @IterableMapping(qualifiedByName = "fromDomain")
  List<DomainDTO> fromDomainIterable(Iterable<DomainEntity> domainEntityIterable);


}
