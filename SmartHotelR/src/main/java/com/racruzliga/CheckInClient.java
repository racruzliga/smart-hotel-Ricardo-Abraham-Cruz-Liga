import java.io.*;
import java.net.*;
import java.util.Scanner;


public class CheckInClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server's IP address (localhost if running locally)
        int serverPort = 8888; // Server's port number

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            // Set up input and output streams for communication with the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Read guest's name and date from user input
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter guest's name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter check-in date (e.g., YYYY-MM-DD): ");
            String checkInDate = scanner.nextLine();

            // Construct check-in data to send to the server
            String guestData = guestName + "," + checkInDate;

            // Send check-in data to the server
            out.println(guestData);

            // Receive response from the server
            String confirmationMessage = in.readLine();
            System.out.println("Server response: " + confirmationMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
