package it.almaviva.eai.um.core.service;

import it.almaviva.eai.um.common.dto.user.UserClaimsUpdateDTO;
import it.almaviva.eai.um.common.dto.user.UserDTO;
import it.almaviva.eai.um.common.dto.user.UserUpdateDTO;
import it.almaviva.eai.um.common.dto.user.UserWithClaimsDTO;
import it.almaviva.eai.um.service.IUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.java.security.SSLProtocolSocketFactory;
import org.apache.axis2.java.security.TrustAllTrustManager;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wso2.carbon.um.ws.api.stub.ClaimDTO;
import org.wso2.carbon.um.ws.api.stub.ClaimValue;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceStub;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceUserStoreExceptionException;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService {


  @Value("${wso2.url}")
  private String wso2url;

  @Value("${wso2.username}")
  private String wso2username;

  @Value("${wso2.password}")
  private String wso2password;

  private  RemoteUserStoreManagerServiceStub adminStub;

  static ClaimValue createClaimValue(String claimURI, String claimValue){
    ClaimValue cv = new ClaimValue();
    cv.setClaimURI(claimURI);
    cv.setValue(claimValue);
    return cv;
  }

  public static String removePrefix(String s, String prefix)
  {
    if (s != null && prefix != null && s.startsWith(prefix)){
      return s.substring(prefix.length());
    }
    return s;
  }


  @PostConstruct
  public void init() throws NoSuchAlgorithmException, KeyManagementException, AxisFault {
    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, new TrustManager[] {new TrustAllTrustManager()}, null);
    String serviceEndPoint = wso2url +"/services/RemoteUserStoreManagerService";
    ConfigurationContext configContext = ConfigurationContextFactory
        .createConfigurationContextFromFileSystem(null, null);
    adminStub = new RemoteUserStoreManagerServiceStub(configContext, serviceEndPoint);
    ServiceClient client = adminStub._getServiceClient();
    Options option = client.getOptions();
    option.setProperty(HTTPConstants.COOKIE_STRING, null);
    option.setProperty(   HTTPConstants.CUSTOM_PROTOCOL_HANDLER,
        new Protocol("https", (ProtocolSocketFactory) new SSLProtocolSocketFactory(sslContext), 443));

    HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
    auth.setUsername(wso2username);
    auth.setPassword(wso2password);
    auth.setPreemptiveAuthentication(true);
    option.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);
    option.setManageSession(true);
  }


  @SneakyThrows
  @Override
  public List<String> getUsers(Optional<String> query, Optional<Integer> limit) {
    String[] users = adminStub.listUsers(query.orElseGet(() -> "*"), limit.orElseGet(() -> -1));
    return Arrays.asList(users == null ? new String[0] : users);
  }

  @SneakyThrows
  @Override
  public List<UserWithClaimsDTO> getUsersWithClaims(Optional<String> query, Optional<Integer> limit) {
    List<UserWithClaimsDTO> result = new ArrayList<>();

    for(String userName : this.getUsers(query,limit)){
      UserWithClaimsDTO user = this.getUser(userName);
      result.add(user);
    }
    return result;
  }

  @Override
  public void createUser(UserDTO userBody) throws Exception {
    List<ClaimValue> claimsFromDTO = new ArrayList<>();

    if(userBody.getAskPassword()){
      claimsFromDTO.add(createClaimValue("http://wso2.org/claims/identity/askPassword", "true"));
    }

    if(userBody.getClaims() != null) {
      claimsFromDTO.addAll(userBody.getClaims().entrySet()
          .stream()
          .map(x ->
              createClaimValue("http://wso2.org/claims/" + x.getKey(),
                  x.getValue()))
          .collect(Collectors.toList()));
    }

    ClaimValue[] values = new ClaimValue[claimsFromDTO.size()];
    values = claimsFromDTO.toArray(values);

    try {
      adminStub.addUser(userBody.getUsername(), userBody.getPassword(), userBody.getRoles(), values, "default", false );
    } catch (RemoteException e) {
      e.printStackTrace();
      throw new Exception(e);
    } catch (RemoteUserStoreManagerServiceUserStoreExceptionException e) {
      e.printStackTrace();
      throw new Exception(e);
    }

  }

  @SneakyThrows
  @Override
  public void updateUser(String userId, UserClaimsUpdateDTO userClaimsUpdateDTO) {

    final Map<String, String> claimsMap = userClaimsUpdateDTO.getClaims();
    List<ClaimValue> claimsFromDTO = claimsMap.entrySet()
        .stream()
        .map (x -> createClaimValue("http://wso2.org/claims/"+x.getKey(), x.getValue()))
        .collect(Collectors.toList());

    ClaimValue[] claims = new ClaimValue[claimsFromDTO.size()];
    claims = claimsFromDTO.toArray(claims);

    adminStub.setUserClaimValues(userId, claims, "default");
  }

  @SneakyThrows
  @Override
  public UserWithClaimsDTO getUser(String userId) {
    ClaimDTO[] userClaimValues = adminStub.getUserClaimValues(userId, "default");
    UserWithClaimsDTO userDTO = UserWithClaimsDTO.builder().username(userId).build();
    for(ClaimDTO claimDTO : userClaimValues){
      String claimUri = claimDTO.getClaimUri();
      String value = claimDTO.getValue();
      userDTO.getClaims().put(removePrefix(claimUri, "http://wso2.org/claims/"),value);
    }

    return userDTO;
  }

  @Override
  public void deleteUser(String userId) throws Exception {
    try {
      adminStub.deleteUser(userId);
    } catch (RemoteException e) {
      e.printStackTrace();
      throw new Exception(e);
    } catch (RemoteUserStoreManagerServiceUserStoreExceptionException e) {
      e.printStackTrace();
      throw new Exception(e);
    }
  }

  @SneakyThrows
  @Override
  public List<String> getRoleListOfUser(String username) {
    String[] roleListOfUser = adminStub.getRoleListOfUser(username);
    return new ArrayList<>(Arrays.asList(roleListOfUser == null ? new String[0] : roleListOfUser));
  }

  @SneakyThrows
  @Override
  public void updateRoleListOfUser(String userId, UserUpdateDTO userUpdateDTO) {
      adminStub.updateRoleListOfUser(userId, userUpdateDTO.getDeletedRoles(), userUpdateDTO.getNewRoles());
  }
}
