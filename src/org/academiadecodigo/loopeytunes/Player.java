package org.academiadecodigo.loopeytunes;


import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player implements Runnable {

    private Question[] questions;
    private Socket playerSocket;
    private int score;
    private Prompt prompt;
    private PrintWriter printWriter;
    private String playerName;
    private boolean completeAnswers = false;


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
        printWriter.println("Welcome to Clash of Cadets " + playerName + "! \n" + "Good luck!" + "\n");
        printWriter.flush();
    }


    public void chooseAnswers() {


        printWriter.println("Choose your answers wisely: \n");
        StringInputScanner chooseAnswer = new StringInputScanner();

        for (Question q : questions) {

            printWriter.println(q.getQuestion() + "\n1: " + q.getResponse());
            printWriter.flush();
            String[] options = q.getOptions();

            List<String> optionsList = Arrays.asList(options);

            for (int i = 0; i < 3; i++) {

                chooseAnswer.setMessage((i + 2) + ": ");
                String message = (prompt.getUserInput(chooseAnswer));

                while (optionsList.contains(message)) {

                    printWriter.println("\nThis answer already exists. Please choose another one \n");
                    printWriter.flush();
                    message = prompt.getUserInput(chooseAnswer);

                }

                q.setOptions(message, i + 1);
            }

        }
        answersCompleted();
    }

    public void answersCompleted() {
        completeAnswers = true;
    }

    public boolean getCompleteAnswers() {
        return completeAnswers;
    }

    public void joinThreads(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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



