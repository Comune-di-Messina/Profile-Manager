package it.almaviva.eai.um.common.dto.role.bulk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = RoleDeleteBulkDTO.RoleDeleteBulkDTOBuilder.class)
@ApiModel("RoleDeleteBulkDTO")
public class RoleDeleteBulkDTO {

  @JsonProperty("deletedRoles")
  private List<String> deletedRoles;

}
