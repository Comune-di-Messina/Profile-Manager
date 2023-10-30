package it.almaviva.eai.um.common.dto.bulk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = BulkPersmissionDTO.BulkPersmissionDTOBuilder.class)
@ApiModel("BulkRoleUpdateDTO")
public class BulkPersmissionDTO {

  @ApiModelProperty
  @JsonProperty(value = "id")
  private String id;

  @ApiModelProperty
  @JsonProperty(value = "newPermissions")
  private String[] newPermissions;

  @ApiModelProperty
  @JsonProperty(value = "deletedPermissions")
  private String[] deletedPermissions;

}
