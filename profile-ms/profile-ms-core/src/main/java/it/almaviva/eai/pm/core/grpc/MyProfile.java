package it.almaviva.eai.pm.core.grpc;

import com.google.protobuf.ProtocolStringList;
import io.grpc.stub.StreamObserver;
import it.almaviva.eai.pm.core.mapper.IProfileMapper;
import it.almaviva.eai.pm.core.service.IProfileService;
import it.almaviva.eai.pm.shared.dto.microservice.ProfileDTO;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class MyProfile extends it.almaviva.eai.pm.core.grpc.MyProfileGrpc.MyProfileImplBase {

    @Autowired
    private IProfileService iProfileService;

    @Autowired
    private IProfileMapper iProfileMapper;

    @Override
    public void myprofile(it.almaviva.eai.pm.core.grpc.ProfileManagerRequest request, StreamObserver<it.almaviva.eai.pm.core.grpc.ProfileManagerResponse> responseObserver) {

        it.almaviva.eai.pm.core.grpc.UUID microserviceId = request.getMicroserviceId();
        ProtocolStringList rolesList = request.getRolesList();
        ProfileDTO profileDto = iProfileService.getProfile(java.util.UUID.fromString(microserviceId.getValue()), rolesList);

        it.almaviva.eai.pm.core.grpc.Profile profile = iProfileMapper.map(profileDto);


        it.almaviva.eai.pm.core.grpc.ProfileManagerResponse reply = it.almaviva.eai.pm.core.grpc.ProfileManagerResponse.newBuilder().setProfile(profile).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }
}
