package it.almaviva.eai.um.common.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = RoleDTO.RoleDTOBuilder.class)
@ApiModel("Role")
public class RoleDTO {

    @NonNull
    @JsonProperty(required = true)
    @ApiModelProperty(required = true)
    String id;

    @JsonProperty(value = "newUsers")
    @ApiModelProperty
    String[] newUsers;

    @JsonProperty(value = "permissions")
    @ApiModelProperty
    String[] permissions;


}
