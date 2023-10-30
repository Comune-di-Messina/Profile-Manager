package it.almaviva.eai.um.common.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = UserClaimsUpdateDTO.UserClaimsUpdateDTOBuilder.class)
@ApiModel("Update Claims User")
public class UserClaimsUpdateDTO {

  @NonNull
  @NotNull
  @JsonProperty( value = "claims")
  @ApiModelProperty
  Map<String, String> claims;


}
