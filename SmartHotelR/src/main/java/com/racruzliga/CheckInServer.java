import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import com.racruzliga.CheckInProto.CheckInRequest;
import com.racruzliga.CheckInProto.CheckInResponse;
import com.racruzliga.CheckInProto.CheckInServiceGrpc;

import java.io.IOException;

public class CheckInServer {
    private int port;
    private Server server;

    public CheckInServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new CheckInServiceImpl())
                .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("Check-in gRPC server started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
            CheckInServer.this.stop();
            System.out.println("Server shut down.");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class CheckInServiceImpl extends CheckInServiceGrpc.CheckInServiceImplBase {
        @Override
        public void performCheckIn(CheckInRequest request, StreamObserver<CheckInResponse> responseObserver) {
            String guestName = request.getGuestName();
            String checkInDate = request.getCheckInDate();

            String confirmationMessage = "Check-in successful for " + guestName + " on " + checkInDate;
            CheckInResponse response = CheckInResponse.newBuilder()
                    .setMessage(confirmationMessage)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8888;
        CheckInServer server = new CheckInServer(port);
        server.start();
        server.blockUntilShutdown();
    }
}
