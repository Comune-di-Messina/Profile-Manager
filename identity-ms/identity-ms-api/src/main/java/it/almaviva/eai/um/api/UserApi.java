package it.almaviva.eai.um.api;

import it.almaviva.eai.um.common.dto.user.UserClaimsUpdateDTO;
import it.almaviva.eai.um.common.dto.user.UserDTO;
import it.almaviva.eai.um.common.dto.user.UserUpdateDTO;
import it.almaviva.eai.um.common.dto.user.UserWithClaimsDTO;
import it.almaviva.eai.um.service.IUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class UserApi implements IUserApi {

    @Autowired
    private IUserService iUserService;

    @Override
    public List<UserWithClaimsDTO> getUsers(Optional<String> fetch, Optional<String> query, Optional<Integer> limit){
        if(fetch.isPresent()) {
                return iUserService.getUsersWithClaims(query, limit);
            }else{
                List<String> usersName = iUserService.getUsers(query, limit);
                return usersName.stream().map(s -> UserWithClaimsDTO.builder().username(s).build()).collect(Collectors.toList());
            }
    }


    @SneakyThrows
    @Override
    public void createUser(UserDTO userBody) {
        iUserService.createUser(userBody);
    }

    @Override
    public void updateUser(String userId, UserClaimsUpdateDTO userClaimsUpdateDTO) {
         iUserService.updateUser(userId, userClaimsUpdateDTO);
    }

    @Override
    public List<String> getRolesOfUser(String userId) {
        List<String> roleListOfUser = iUserService.getRoleListOfUser(userId);
        roleListOfUser.removeIf(s -> s.startsWith("Application/"));
        return roleListOfUser;
    }

    @SneakyThrows
    @Override
    public void deleteUser(String userId) {
        iUserService.deleteUser(userId);
    }

    @Override
    public void updateRoleListOfUser(String userId, UserUpdateDTO userUpdateDTO) {
        iUserService.updateRoleListOfUser(userId, userUpdateDTO);
    }


    @Override
    public List<String> getApplicationsOfUser(String userId) {
        List<String> roleListOfUser = iUserService.getRoleListOfUser(userId);
        roleListOfUser.removeIf(s -> !s.startsWith("Application/"));
        return roleListOfUser;
    }

    @Override
    public void updateApplicationsListOfUser(String userId, UserUpdateDTO userUpdateDTO) {
        iUserService.updateRoleListOfUser(userId, userUpdateDTO);
    }


}
