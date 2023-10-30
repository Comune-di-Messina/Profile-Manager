package it.almaviva.eai.um.common.dto.bulk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = BulkRoleUpdateDTO.BulkRoleUpdateDTOBuilder.class)
@ApiModel("BulkRoleUpdateDTO")
public class BulkRoleUpdateDTO {

  @ApiModelProperty
  @JsonProperty(value = "id")
  String id;

  @ApiModelProperty
  @JsonProperty(value = "newUsers")
  String[] newUsers;

  @JsonProperty(value = "deletedUsers")
  @ApiModelProperty
  String[] deletedUsers;

}
