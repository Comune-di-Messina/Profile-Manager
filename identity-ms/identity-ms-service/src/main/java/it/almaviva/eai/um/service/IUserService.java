package it.almaviva.eai.um.service;

import it.almaviva.eai.um.common.dto.user.UserClaimsUpdateDTO;
import it.almaviva.eai.um.common.dto.user.UserDTO;
import it.almaviva.eai.um.common.dto.user.UserUpdateDTO;
import it.almaviva.eai.um.common.dto.user.UserWithClaimsDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<String> getUsers(Optional<String> query, Optional<Integer> limit);

    List<UserWithClaimsDTO> getUsersWithClaims(Optional<String> query, Optional<Integer> limit);

    void createUser(UserDTO userBody) throws Exception;

    void  updateUser(String userId, UserClaimsUpdateDTO userClaimsUpdateDTO) ;

    UserWithClaimsDTO getUser(String userId) ;

    void deleteUser(String userId) throws Exception;

    List<String> getRoleListOfUser(String userId);

    void updateRoleListOfUser(String userId, UserUpdateDTO userUpdateDTO);
}
