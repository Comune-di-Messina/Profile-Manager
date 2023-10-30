package it.almaviva.eai.um.common.dto.user.bulk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = UserDeleteBulkDTO.UserDeleteBulkDTOBuilder.class)
@ApiModel("UserDeleteBulkDTO")
public class UserDeleteBulkDTO {

  @JsonProperty("deletedUsers")
  private String[] deletedUsers;

}
