package it.almaviva.eai.um.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
@Builder(toBuilder = true)
@ApiModel("User Fetch")
public class UserWithClaimsDTO {

  @ApiModelProperty
  String username;

  @ApiModelProperty
  Map<String, String> claims = new HashMap<>();

}
