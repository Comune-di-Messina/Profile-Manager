package it.almaviva.eai.um.common.dto.role.bulk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = RoleUpdateBulkDTO.RoleUpdateBulkDTOBuilder.class)
@ApiModel("RoleUpdateBulkDTO")
public class RoleUpdateBulkDTO {

  @NonNull
  @ApiModelProperty(required = true)
  @JsonProperty(value="updatedRoles", required = true)
  private List<RoleItemUpdateBulkDTO> updatedRoles;

}
