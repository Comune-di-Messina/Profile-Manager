package it.almaviva.eai.pm.integration.repository;

import it.almaviva.eai.pm.integration.entity.MicroserviceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMicroserviceRepository extends CrudRepository<MicroserviceEntity, UUID> {


  @Query("SELECT  DISTINCT me FROM MicroserviceEntity me LEFT JOIN FETCH me.roles LEFT JOIN FETCH me.groups LEFT JOIN FETCH me.actions where me.id = :id")
  MicroserviceEntity findMembershipsByMicroserviceId(@Param("id") UUID id);

  Iterable<MicroserviceEntity> findMicroserviceEntitiesByGroups_Id(UUID groupId);
  Iterable<MicroserviceEntity> findMicroserviceEntitiesByRoles_Id(UUID roleId);
}
