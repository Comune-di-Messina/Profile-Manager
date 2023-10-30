package it.almaviva.eai.ljsa.sdk.core.service;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import it.almaviva.eai.pm.core.grpc.MyProfileGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;


@Service
@ConditionalOnProperty(
    value = "ljsa.profilemanager.enabled",
    havingValue = "true"
)
public class LjsaService {

  @Value("${ljsa.profilemanager.micro-service-id}")
  private UUID microServiceId;

  @Value("${ljsa.profilemanager.contact-point}")
  private String contactPoint;


  public it.almaviva.eai.pm.core.grpc.Profile getCachedProfile(Collection<?> roles) {


    String[] arrOfStr = contactPoint.split(":", 2);

    ManagedChannel channel = ManagedChannelBuilder.forAddress(arrOfStr[0], Integer.valueOf(arrOfStr[1])).usePlaintext().build();

    MyProfileGrpc.MyProfileBlockingStub myProfileBlockingStub = MyProfileGrpc.newBlockingStub(channel);


    it.almaviva.eai.pm.core.grpc.UUID build = it.almaviva.eai.pm.core.grpc.UUID.newBuilder().setValue(microServiceId.toString()).build();

    it.almaviva.eai.pm.core.grpc.ProfileManagerRequest request = it.almaviva.eai.pm.core.grpc.ProfileManagerRequest.newBuilder().setMicroserviceId(build).addAllRoles((Iterable<String>) roles).build();
    return myProfileBlockingStub.myprofile(request).getProfile();
  }
}
