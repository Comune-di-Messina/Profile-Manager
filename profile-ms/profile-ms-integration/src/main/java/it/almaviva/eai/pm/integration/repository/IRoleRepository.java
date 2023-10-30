package it.almaviva.eai.pm.integration.repository;

import it.almaviva.eai.pm.integration.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface IRoleRepository extends CrudRepository<RoleEntity, UUID> {

  Iterable<RoleEntity> findRoleEntitiesByMicroservice_Id(UUID uuid);

  @Query("SELECT DISTINCT re FROM RoleEntity re LEFT JOIN FETCH re.permissions p INNER JOIN FETCH re.microservice ms LEFT JOIN FETCH re.actions a WHERE ms.id = ?1 and re.wso2name in ?2")
  Iterable<RoleEntity> findRoleEntitiesByMicroservice_IdAndWso2nameIn(UUID microserviceId, Collection<String> wso2roles);

}
