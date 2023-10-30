package it.almaviva.eai.pm.integration.repository;

import it.almaviva.eai.pm.integration.entity.ActionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IActionRepository extends CrudRepository<ActionEntity, UUID> {
  Iterable<ActionEntity> findActionEntitiesByRoles_Id(UUID uuid);

  Iterable<ActionEntity> findActionEntitiesByMicroservice_Id(UUID uuid);

}
