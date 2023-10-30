package it.almaviva.eai.um.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@ApiModel("KOMessageDTO")
@JsonDeserialize(builder = KOMessageDTO.KOMessageDTOBuilder.class)
public class KOMessageDTO {

  @JsonProperty(value = "id")
  private String id;

  @JsonProperty(value = "message")
  private String message;

}
