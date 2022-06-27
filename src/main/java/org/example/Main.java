package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        // Start receiving messages
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started.\n Listening for messages.");
            while (true) {
                // Handle new incoming message

                try (Socket client = serverSocket.accept()) {
                    //client <-- messages queued up in it!!
                    System.out.println("Debug: new message received " + client.toString());
                    InputStreamReader isr = new InputStreamReader(client.getInputStream());

                    BufferedReader br = new BufferedReader(isr);

                    StringBuilder request = new StringBuilder();

                    String line;
                    line = br.readLine();
                    while (!line.isBlank()) {
                        request.append(line +"\r\n");
                        line = br.readLine();
                    }

                    System.out.println("--Request--");
                    System.out.println(request);
                    // send a "hello world"
                    OutputStream clientOutput = client.getOutputStream();
                    clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                    clientOutput.write(("\r\n").getBytes());
                    clientOutput.write(("Hello World\r\n").getBytes());
                    clientOutput.flush();

                //client.close();
                }
            }

        }
    }
}