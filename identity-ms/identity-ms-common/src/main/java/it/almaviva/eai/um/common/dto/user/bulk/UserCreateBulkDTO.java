package it.almaviva.eai.um.common.dto.user.bulk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import it.almaviva.eai.um.common.dto.user.UserDTO;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = UserCreateBulkDTO.UserCreateBulkDTOBuilder.class)
@ApiModel("UserCreateBulkDTO")
public class UserCreateBulkDTO{

	@JsonProperty("newUsers")
	private List<UserDTO> newUsers;
}
