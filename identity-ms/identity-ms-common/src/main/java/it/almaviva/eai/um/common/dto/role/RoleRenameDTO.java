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
@JsonDeserialize(builder = RoleRenameDTO.RoleRenameDTOBuilder.class)
@ApiModel("Role Rename")
public class RoleRenameDTO {

    @NonNull
    @JsonProperty(required = true, value = "newName")
    @ApiModelProperty(required = true)
    String newName;
}
