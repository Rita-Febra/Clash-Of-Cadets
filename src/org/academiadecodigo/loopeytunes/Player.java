package org.academiadecodigo.loopeytunes;


import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Player implements Runnable {

    private Question[] questions;
    private Socket playerSocket;
    private int score = 0;
    private Prompt prompt;
    private PrintWriter printWriter;
    private String playerName;
    private boolean gameOn;
    private boolean choicesMade;
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


    public boolean isGameOn(){
        return gameOn;
    }

    public boolean choicesAreMade() {
        return choicesMade;
    }

    public void switchQuestions(Question[] questions) {
        this.questions = questions;
        gameOn = true;
    }

    public int getScore(){
        return score;
    }

    public String getPlayerName(){
        return playerName;
    }


    public void menu() {

        String[] options = {"Start", "Rules"};
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage("Choose option: \n");

        int answerIndex = prompt.getUserInput(scanner);

    }

    private void username() {

        StringInputScanner username = new StringInputScanner();
        username.setMessage("Username: ");

        playerName = prompt.getUserInput(username);
        printWriter.println("\nWelcome to Clash of Cadets " + playerName + "! \n" + "Good luck! \n");
        printWriter.flush();
    }


    public void chooseAnswers() {


        printWriter.println("Choose your answers wisely: \n");
        StringInputScanner chooseAnswer = new StringInputScanner();

        for (Question q : questions) {

            printWriter.println(q.getQuestion() + "\n1: " + q.getAnswer());
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

            Collections.shuffle(optionsList);
            optionsList.toArray(options);
        }

        answersCompleted();

    }

    public void answersCompleted() {
        completeAnswers = true;
    }

    private void play() {
        for (Question q : questions) {
            MenuInputScanner scanner = new MenuInputScanner(q.getOptions());
            scanner.setMessage(q.getQuestion());

            int answerIndex = prompt.getUserInput(scanner);

            if (q.getOptions()[answerIndex - 1].equals(q.getAnswer())) {
                score += 10;
                printWriter.println("\nCorrect Answer! 10 points for you.");
                printWriter.flush();

            }  else {

                printWriter.println("\nWrong Answer! No points for you.");
                printWriter.flush();
            }

            printWriter.println("\nYour current score is: " + getScore());
            printWriter.flush();
        }

        printWriter.println("\nYour final score is: " + getScore());
        printWriter.flush();
        gameOn = false;
    }


    @Override
    public void run() {

        username();
        chooseAnswers();
        choicesMade = true;

        // Thread.sleep
        while (!gameOn) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        play();

    }

    public void wins() {
        try {
            printWriter = new PrintWriter(playerSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        printWriter.println("\n## YOU HAVE WON THE GAME! CONGRATULATIONS ##\n");
        printWriter.flush();
    }

    public void loses() {
        try {
            printWriter = new PrintWriter(playerSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        printWriter.println("\n## SORRY BUT YOU HAVE LOST THE GAME, TRY AGAIN NEXT TIME ##\n");
        printWriter.flush();
    }

}



