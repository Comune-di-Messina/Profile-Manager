package it.almaviva.eai.pm.shared.dto.microservice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("WSO2Profile")
@Data
public class WSO2ProfileDTO {
  @ApiModelProperty
  private List<String> roles;
  @ApiModelProperty
  private List<String> groups;
}
