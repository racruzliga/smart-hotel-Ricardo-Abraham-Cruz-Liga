package user;

import com.racruzliga.grpc.User.APIResponse;
import com.racruzliga.grpc.User.Empty;
import com.racruzliga.grpc.User.LoginRequest;
import com.racruzliga.grpc.userGrpc.userImplBase;

import io.grpc.stub.StreamObserver;

public class UserService extends userImplBase {

    @Override
    public void login(LoginRequest request, StreamObserver<APIResponse> responseObserver) {
        System.out.println("Inside login");

        String username = request.getUsername();
        String password = request.getPassword();

        // Create a response builder
        APIResponse.Builder responseBuilder = APIResponse.newBuilder();

        if (isValidLogin(username, password)) {
            // Valid credentials
            APIResponse response = responseBuilder.setResponseCode(0).setResponsemessage("SUCCESS").build();
            responseObserver.onNext(response);
        } else {
            // Invalid credentials
            APIResponse response = responseBuilder.setResponseCode(100).setResponsemessage("INVALID PASSWORD").build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void logout(Empty request, StreamObserver<APIResponse> responseObserver) {
        // For simplicity, assume logout always succeeds
        APIResponse response = APIResponse.newBuilder()
                .setResponseCode(0)
                .setResponsemessage("LOGOUT_SUCCESS")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private boolean isValidLogin(String username, String password) {
        // Implement your login validation logic here
        // For example, you can check against a database or hardcoded credentials
        return username.equals(password);  // Dummy validation (username equals password)
    }
}
