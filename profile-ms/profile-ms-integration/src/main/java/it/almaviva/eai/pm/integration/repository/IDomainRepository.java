package it.almaviva.eai.pm.integration.repository;

import it.almaviva.eai.pm.integration.entity.DomainEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDomainRepository extends CrudRepository<DomainEntity, UUID> {

  @Query("SELECT DISTINCT de FROM DomainEntity de LEFT JOIN FETCH de.domainvalues dv WHERE de.id = (:id)")
  DomainEntity findByIdAndFetchDomainvaluesEagerly(@Param("id") UUID id);


}
