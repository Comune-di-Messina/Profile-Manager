package it.almaviva.eai.um.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = ResponseDTO.ResponseDTOBuilder.class)
@ApiModel("ResponseDTO")
public class ResponseDTO {

  @JsonProperty("ko")
  private List<KOMessageDTO> ko;

}
