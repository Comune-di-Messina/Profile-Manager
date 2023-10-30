package it.almaviva.eai.pm.shared.dto.microservice;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateMicroserviceDTO {

  @NotNull
  @NotEmpty
  private String name;

  private String desc;
}
