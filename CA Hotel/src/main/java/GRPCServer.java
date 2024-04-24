import io.grpc.Server;
import io.grpc.ServerBuilder;
import user.UserService;

public class GRPCServer {

    public static void main(String[] args) {
        try {
            // Create a new gRPC server on port 9099
            Server server = ServerBuilder.forPort(9099)
                    .addService(new UserService())  // Add UserService implementation
                    .build();

            // Start the server
            Server start = server.start();
            System.out.println("Server started at port " + server.getPort());

            // Block until the server is terminated
            server.awaitTermination();
        } catch (Exception e) {
            System.err.println("Error starting gRPC server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
