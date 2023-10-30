package it.almaviva.eai.um.common.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = UserUpdateDTO.UserUpdateDTOBuilder.class)
@ApiModel("Update User")
public class UserUpdateDTO {

    @ApiModelProperty
    @JsonProperty(value = "newRoles")
    String[] newRoles;

    @ApiModelProperty
    @JsonProperty(value = "deletedRoles")
    String[] deletedRoles;




}
