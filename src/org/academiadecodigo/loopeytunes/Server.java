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
    private String playerName;
    private PrintWriter message;



    public Server() {

        try {
            System.out.println("Binding to port: " + serverPort);
            serverSocket = new ServerSocket(serverPort);

            while (!serverSocket.isClosed()) {

                System.out.println("Waiting for player...");
                clientSocket = serverSocket.accept();
                System.out.println("Player " + playerName + " connected to game server!");

                if (numConnect < 2) {
                    sockets[numConnect] = clientSocket;
                    message = new PrintWriter(clientSocket.getOutputStream(), true);
                    message.println(playerName + " connected to game server");
                    message.flush();
                    numConnect++;
                } else {
                    message = new PrintWriter(clientSocket.getOutputStream(), true);
                    message.println("Server is full. Please try again when the game is over :)");
                    message.flush();
                    clientSocket.close();

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        Server gameServer = new Server();

    }


}
