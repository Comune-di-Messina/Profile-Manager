package it.almaviva.eai.um.common.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Value
@Builder(toBuilder = true)
@ApiModel("User Create")
@JsonDeserialize(builder = UserDTO.UserDTOBuilder.class)
public class UserDTO {

    @ApiModelProperty( required = true)
    @JsonProperty(required = true)
    @NotBlank(message = "username is mandatory")
    @NonNull
    String username;

    @ApiModelProperty( required = true)
    @JsonProperty(required = true)
    String password;

    @NonNull
    @JsonProperty(required = true)
    @ApiModelProperty
    Boolean askPassword;

    @ApiModelProperty
    @JsonProperty(value = "roles")
    String[] roles;

    @ApiModelProperty
    @JsonProperty(value = "claims")
    Map<String, String> claims;

}
