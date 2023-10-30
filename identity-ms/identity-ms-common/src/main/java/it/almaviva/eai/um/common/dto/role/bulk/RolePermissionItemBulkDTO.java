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
@JsonDeserialize( builder = RolePermissionItemBulkDTO.RolePermissionItemBulkDTOBuilder.class)
@ApiModel("RolePermissionItemBulkDTO")
public class RolePermissionItemBulkDTO {

  @NonNull
  @ApiModelProperty(required = true)
  @JsonProperty(value="id", required = true)
  private String id;

  @ApiModelProperty
  @JsonProperty(value="authorizePermissions")
  private List<String> authorizePermissions;

  @ApiModelProperty
  @JsonProperty(value="denyPermissions")
  private List<String> denyPermissions;

}
