package it.almaviva.eai.um.common.dto.role.bulk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.eai.um.common.dto.role.RoleDTO;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = RoleCreateBulkDTO.RoleCreateBulkDTOBuilder.class)
@ApiModel("RoleCreateBulkDTO")
public class RoleCreateBulkDTO {

  @NonNull
  @ApiModelProperty(required = true)
  @JsonProperty(value="newRoles", required = true)
  private List<RoleDTO> newRoles;

}
