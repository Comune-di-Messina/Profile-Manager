package it.almaviva.eai.um.core.service;

import it.almaviva.eai.um.service.IAuthorizationService;
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
import org.wso2.carbon.um.ws.api.stub.RemoteAuthorizationManagerServiceStub;
import org.wso2.carbon.um.ws.api.stub.UserStoreExceptionException;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthorizationService implements IAuthorizationService {

  @Value("${wso2.url}")
  private String url;

  @Value("${wso2.username}")
  private String wso2username;

  @Value("${wso2.password}")
  private String wso2password;

  private RemoteAuthorizationManagerServiceStub adminStub;


  @PostConstruct
  public void init() throws NoSuchAlgorithmException, KeyManagementException, AxisFault {
    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, new TrustManager[]{new TrustAllTrustManager()}, null);
    String serviceEndPoint = url + "/services/RemoteAuthorizationManagerService";
    ConfigurationContext configContext = ConfigurationContextFactory
        .createConfigurationContextFromFileSystem(null, null);
    adminStub = new RemoteAuthorizationManagerServiceStub(configContext, serviceEndPoint);
    ServiceClient client = adminStub._getServiceClient();
    Options option = client.getOptions();
    option.setProperty(HTTPConstants.COOKIE_STRING, null);
    option.setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER,
        new Protocol("https", (ProtocolSocketFactory) new SSLProtocolSocketFactory(sslContext),
            443));

    HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
    auth.setUsername(wso2username);
    auth.setPassword(wso2password);
    auth.setPreemptiveAuthentication(true);
    option.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);
    option.setManageSession(true);
  }

  @Override
  public void authorizeRole(String roleName, String resourceId) throws Exception {
    try {
      adminStub.authorizeRole(roleName, resourceId, "ui.execute");
    } catch (RemoteException e) {
      e.printStackTrace();
      throw new Exception(e);
    } catch (UserStoreExceptionException e) {
      e.printStackTrace();
      throw new Exception(e);
    }
  }

  @SneakyThrows
  @Override
  public void clearAllRoleAuthorization(String roleName) {
    adminStub.clearAllRoleAuthorization(roleName);
  }

  @SneakyThrows
  @Override
  public void clearResourceAuthorizations(String resourceId) {
    adminStub.clearResourceAuthorizations(resourceId);
  }

  @SneakyThrows
  @Override
  public void clearRoleAuthorization(String roleName, String resourceId) {
    adminStub.clearRoleAuthorization(roleName, resourceId, "ui.execute");
  }

  @Override
  public void denyRole(String roleName, String resourceId) throws Exception {
    try {
      adminStub.denyRole(roleName, resourceId, "ui.execute");
    } catch (RemoteException e) {
      e.printStackTrace();
      throw new Exception(e);
    } catch (UserStoreExceptionException e) {
      e.printStackTrace();
      throw new Exception(e);
    }
  }

  @SneakyThrows
  @Override
  public List<String> getAllowedRolesForResource(String resourceId) {
    return Arrays.asList(adminStub.getAllowedRolesForResource(resourceId, "ui.execute"));
  }

  @SneakyThrows
  @Override
  public boolean isRoleAuthorized(String roleName, String resourceId) {
    return adminStub.isRoleAuthorized(roleName, resourceId, "ui.execute");
  }

}
