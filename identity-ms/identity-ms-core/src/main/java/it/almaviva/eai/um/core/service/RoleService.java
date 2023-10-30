package it.almaviva.eai.um.core.service;


import it.almaviva.eai.um.common.dto.role.RoleDTO;
import it.almaviva.eai.um.common.dto.role.RoleRenameDTO;
import it.almaviva.eai.um.common.dto.role.RoleUpdateDTO;
import it.almaviva.eai.um.service.IRoleService;
import lombok.SneakyThrows;
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
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceStub;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceUserStoreExceptionException;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleService  implements IRoleService {

  @Value("${wso2.url}")
  private String url;

  @Value("${wso2.username}")
  private String wso2username;

  @Value("${wso2.password}")
  private String wso2password;

  private  RemoteUserStoreManagerServiceStub adminStub;

  @PostConstruct
  public void init() throws NoSuchAlgorithmException, KeyManagementException, AxisFault {
    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, new TrustManager[] {new TrustAllTrustManager()}, null);
    String serviceEndPoint = url +"/services/RemoteUserStoreManagerService";
    ConfigurationContext configContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);
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
  public List<String> getAllRoles()  {
    String[] roleNames = adminStub.getRoleNames();
    List<String> roleList = new ArrayList<>(Arrays.asList(roleNames == null ? new String[0] : roleNames));
    return roleList;
  }

  @Override
  public void createRole(RoleDTO roleBody) throws Exception {
    try {
      adminStub.addRole(roleBody.getId(), roleBody.getNewUsers(), null);
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
  public List<String> getUserListOfRole(String roleId) {
    String[] userListOfRole = adminStub.getUserListOfRole(roleId);
    return Arrays.asList(userListOfRole == null ? new String[0] : userListOfRole);
  }

  @Override
  public void deleteRole(String roleId) throws Exception {
    try {
      adminStub.deleteRole(roleId);
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
  public void updateRole(String roleId, RoleUpdateDTO roleDTO) {
    adminStub.updateUserListOfRole(roleId, roleDTO.getDeletedUsers(), roleDTO.getNewUsers());
  }

  @SneakyThrows
  @Override
  public void renameRole(String roleId, RoleRenameDTO roleDTO) {
    adminStub.updateRoleName(roleId, roleDTO.getNewName());
  }


}
