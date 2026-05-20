package netlab2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 4999;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Client is ready.");

            while (true) {
                System.out.print("Enter command (UPPER:text / LOWER:text / REVERSE:text / COUNT:text / VOWELS:text / DATE / TIME / QUIT): ");
                String text = scanner.nextLine();

                out.println(text);
                String response = in.readLine();

                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }

                System.out.println("Response: " + response);

                if (text.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
