package it.almaviva.eai.pm.shared.dto.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CreateDomainDTO {
  @NotNull
  private String name;
  @NotNull
  private String wso2name;
  private String desc;
}
