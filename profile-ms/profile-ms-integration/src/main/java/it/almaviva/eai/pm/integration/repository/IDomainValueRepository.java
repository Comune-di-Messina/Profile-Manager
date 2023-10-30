package it.almaviva.eai.pm.integration.repository;

import it.almaviva.eai.pm.integration.entity.DomainvalueEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDomainValueRepository extends CrudRepository<DomainvalueEntity, UUID> {
  Iterable<DomainvalueEntity> findAllByGroups_Id(UUID groupId);

  @Query("SELECT dv FROM DomainvalueEntity dv JOIN FETCH dv.domain d JOIN fetch dv.groups g WHERE g.id = ?1")
  Iterable<DomainvalueEntity> findAllByGroups_IdWithDomain(UUID groupId);

  Iterable<DomainvalueEntity> findAllByDomain_Id(UUID domainId);
}
