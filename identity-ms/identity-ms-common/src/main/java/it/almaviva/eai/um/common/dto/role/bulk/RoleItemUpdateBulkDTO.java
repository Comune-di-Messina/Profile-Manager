package it.almaviva.eai.um.common.dto.role.bulk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize( builder = RoleItemUpdateBulkDTO.RoleItemUpdateBulkDTOBuilder.class)
@ApiModel("RoleUpdateItemBulkDTO")
public class RoleItemUpdateBulkDTO {

  @ApiModelProperty
  @JsonProperty(value = "id")
  private String id;

  @ApiModelProperty
  @JsonProperty(value = "newUsers")
  private String[] newUsers;

  @JsonProperty(value = "deletedUsers")
  @ApiModelProperty
  private String[] deletedUsers;



}
