package it.almaviva.eai.pm.shared.dto.action;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ActionDTO {

  @NotNull
  private String name;
  @NotNull
  private String description;

  private UUID id;

}
