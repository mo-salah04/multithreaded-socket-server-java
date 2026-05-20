package netlab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Clientthreads extends Thread {
    private static final AtomicInteger CLIENT_COUNTER = new AtomicInteger(0);
    private static final String LOG_FILE = "server_log.txt";

    private final Socket socket;
    private final int id;

    public Clientthreads(Socket socket) {
        this.socket = socket;
        this.id = CLIENT_COUNTER.incrementAndGet();
    }

    @Override
    public void run() {
        try (Socket clientSocket = socket;
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String clientAddress = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
            System.out.println("Client " + id + " connected from " + clientAddress);
            writeLog("Client " + id + " connected from " + clientAddress);

            while (true) {
                String msg = in.readLine();

                if (msg == null) {
                    break;
                }

                if (msg.trim().isEmpty()) {
                    out.println("ERROR: Empty command.");
                    continue;
                }

                if (msg.equalsIgnoreCase("QUIT")) {
                    out.println("Disconnected");
                    break;
                }

                String response = processCommand(msg);
                System.out.println("Client " + id + " Command: " + msg + " Result: " + response);
                writeLog("Client " + id + " | Request: " + msg + " | Response: " + response);
                out.println(response);
            }

            System.out.println("Client " + id + " disconnected");
            writeLog("Client " + id + " disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String processCommand(String msg) {
        String[] parts = msg.split(":", 2);
        String command = parts[0].trim().toUpperCase();
        String operand = (parts.length > 1) ? parts[1] : "";

        switch (command) {
            case "TIME":
                return LocalTime.now().toString();
            case "DATE":
                return LocalDate.now().toString();
            case "UPPER":
                return operand.toUpperCase();
            case "LOWER":
                return operand.toLowerCase();
            case "REVERSE":
                return reverse(operand);
            case "COUNT":
                return String.valueOf(operand.length());
            case "VOWELS":
                return String.valueOf(countVowels(operand));
            default:
                return "ERROR: Unknown command.";
        }
    }

    public static String reverse(String text) {
        return new StringBuilder(text).reverse().toString();
    }

    public static int countVowels(String text) {
        int count = 0;
        String lowerText = text.toLowerCase();

        for (int i = 0; i < lowerText.length(); i++) {
            char current = lowerText.charAt(i);
            if (current == 'a' || current == 'e' || current == 'i' || current == 'o' || current == 'u') {
                count++;
            }
        }

        return count;
    }

    private static void writeLog(String line) {
        synchronized (Clientthreads.class) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
                writer.write("[Time: " + LocalDate.now() + " | " + LocalTime.now() + "] " + line);
                writer.newLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
