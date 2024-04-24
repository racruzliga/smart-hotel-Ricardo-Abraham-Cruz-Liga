import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.racruzliga.grpc.User.LoginRequest;
import com.racruzliga.grpc.User.APIResponse;
import com.racruzliga.grpc.userGrpc;
import com.racruzliga.grpc.userGrpc.userBlockingStub;

public class GrpcClient {

    public static void main(String[] args) {
        // Create a channel to connect to the gRPC server running on localhost:9099
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9099)
                                                      .usePlaintext()
                                                      .build();
        
        // Create a stub for the user service
        userBlockingStub userStub = userGrpc.newBlockingStub(channel);
        
        // Create a login request
        LoginRequest loginRequest = LoginRequest.newBuilder()
                                                .setUsername("RAM")
                                                .setPassword("RAM")
                                                .build();
        
        // Call the login method using the stub
        APIResponse response = userStub.login(loginRequest);
        
        // Print the response message
        System.out.println(response.getResponsemessage());

        // Shutdown the channel
        channel.shutdownNow();
    }
}
