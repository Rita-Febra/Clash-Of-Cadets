package org.academiadecodigo.loopeytunes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private final int serverPort = 6666;
    private Socket[] sockets = new Socket[2];
    private int numConnect = 0;
    private Socket clientSocket;

    public Server() {

        try {
            System.out.println("Binding to port: " + serverPort);
            serverSocket = new ServerSocket(serverPort);

            while (!serverSocket.isClosed()) {

                System.out.println("Waiting for player...");
                clientSocket = serverSocket.accept();

                if (numConnect < 2) {

                    sockets[numConnect] = clientSocket;
                    numConnect++;
                } else {
                    PrintWriter fullServer = new PrintWriter(clientSocket.getOutputStream(), true);
                    fullServer.println("Server is full. Please try later");
                    fullServer.flush();
                    clientSocket.close();

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        Server server = new Server();

    }


}
