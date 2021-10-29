package org.academiadecodigo.loopeytunes;


import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable {

    private Question[] questions;
    private Socket playerSocket;
    private int score;
    private String name;
    private Prompt prompt;
    private PrintWriter printWriter;
    private String playerName;


    public Player(Socket playerSocket, Question[] questions) {

        this.playerSocket = playerSocket;
        this.questions = questions;
        try {
            printWriter = new PrintWriter(playerSocket.getOutputStream());
            prompt = new Prompt(playerSocket.getInputStream(), new PrintStream(playerSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void username() {

        StringInputScanner username = new StringInputScanner();
        username.setMessage("Username: ");

        playerName = prompt.getUserInput(username);
        printWriter.println("Welcome to Clash of Cadets " + playerName + "! \n" + "Good luck!");
        printWriter.flush();
    }

    public void chooseAnswers(){
        for (Question q : questions){
            printWriter.println(q.getQuestion() + "\n 1: " + q.getResponse());
            printWriter.flush();
            String[] options = q.getOptions();
            for (int i=0; i<3; i++){
                StringInputScanner chooseAnswer = new StringInputScanner();
                chooseAnswer.setMessage((i+2) + ": ");
                options[i+1] = prompt.getUserInput(chooseAnswer);
                System.out.println(options[i+1]);
            }

            q.setOptions(options);
        }
    }

    public void menu() {

        String[] options = {"Start", "Rules"};
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage("Choose option: \n");

        int answerIndex = prompt.getUserInput(scanner);


    }


    @Override
    public void run() {

        username();
        chooseAnswers();


    }
}


