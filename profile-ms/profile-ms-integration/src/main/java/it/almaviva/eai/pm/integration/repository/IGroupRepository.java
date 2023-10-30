package it.almaviva.eai.pm.integration.repository;

import it.almaviva.eai.pm.integration.entity.GroupEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface IGroupRepository extends CrudRepository<GroupEntity, UUID> {

  Iterable<GroupEntity> findGroupEntitiesByMicroservices_Id(UUID uuid);

  @Query("SELECT DISTINCT ge FROM GroupEntity ge INNER JOIN FETCH ge.microservices ms LEFT JOIN FETCH ge.domainvalues dv LEFT JOIN FETCH dv.domain WHERE ms.id = ?1 and ge.wso2name in ?2")
  Iterable<GroupEntity> findGroupEntitiesByMicroservices_IdAndWso2nameIn(UUID microserviceId, Collection<String> wso2groups);

}
