package it.almaviva.eai.pm.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;
@Data
public class ActionData {

  @JsonProperty(access = Access.READ_ONLY)
  @ApiModelProperty(readOnly = true)
  private UUID id;

  @JsonProperty
  @NotNull
  @ApiModelProperty
  private String name;

  @JsonProperty
  @ApiModelProperty
  @NotNull
  private String desc;
  }

