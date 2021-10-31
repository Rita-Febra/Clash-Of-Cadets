package org.academiadecodigo.loopeytunes;

import org.academiadecodigo.bootcamp.Prompt;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private final int serverPort = 6666;
    private Socket[] sockets = new Socket[2];
    private int numConnect = 0;
    private Socket clientSocket;
    private PrintWriter[] message = new PrintWriter[3];
    private Prompt[] prompt = new Prompt[2];


    public Server() {

        try {
            System.out.println("Binding to port: " + serverPort);
            serverSocket = new ServerSocket(serverPort);

            while (!serverSocket.isClosed()) {

                if (numConnect == 2) {
                    Game game = new Game(sockets[0], sockets[1]);
                    Thread thread = new Thread(game);
                    thread.start();
                    numConnect = 0;
                }

                System.out.println("Waiting for player...");
                clientSocket = serverSocket.accept();
                System.out.println("You connected to game server!");

                if (numConnect < 2) {
                    sockets[numConnect] = clientSocket;
                    message[numConnect] = new PrintWriter(clientSocket.getOutputStream(), true);
                    prompt[numConnect] = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
                    message[numConnect].println("You connected to game server");
                    title();
                    message[numConnect].flush();
                    numConnect++;

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void title() {
        message[numConnect].println("\n"+
                "       ___   _        _     ___   _  _    \n" +
                "      / __| | |      /_\\   / __| | || |   \n" +
                "     | (__  | |__   / _ \\  \\__ \\ | __ |   \n" +
                "      \\___| |____| /_/ \\_\\ |___/ |_||_|   \n" +
                "                 ___    ___               \n" +
                "                / _ \\  | __|              \n" +
                "               | (_) | | _|               \n" +
                "                \\___/  |_|                \n" +
                "   ___     _     ___    ___   _____   ___ \n" +
                "  / __|   /_\\   |   \\  | __| |_   _| / __|\n" +
                " | (__   / _ \\  | |) | | _|    | |   \\__ \\\n" +
                "  \\___| /_/ \\_\\ |___/  |___|   |_|   |___/\n" +
                "                                          ");
        message[numConnect].println("\nGAME RULES\n" + "1. MAKE A WRONG ANSWER TO FOOL YOUR ENEMY \n" + "2. DO NOT REPEAT ANSWERS\n");
        message[numConnect].flush();

    }

    public static void main(String[] args) {

        new Server();


    }


}
