package netlab2;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 4999;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server waiting for clients on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                new Clientthreads(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
