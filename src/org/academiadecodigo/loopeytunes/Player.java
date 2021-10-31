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
    private boolean completeAnswers;


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

    private void username() {

        StringInputScanner username = new StringInputScanner();
        username.setMessage("Choose your name: ");

        playerName = prompt.getUserInput(username);
        printWriter.println("\nWELCOME TO CLASH OF CADETS " + playerName +"! \n" + "GOOD LUCK! \n");
        printWriter.flush();
    }

    public void chooseAnswers() {


        printWriter.println("Choose your answers wisely: \n");
        StringInputScanner chooseAnswer = new StringInputScanner();

        for (Question q : questions) {

            printWriter.println(q.getQuestion() + "\n" + "1: " + q.getAnswer());
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

        printWriter.println("\n\n------READY-------\n\n-------SET--------\n\n--------GO--------\n");
        printWriter.flush();
        for (Question q : questions) {
            MenuInputScanner scanner = new MenuInputScanner(q.getOptions());
            scanner.setMessage(q.getQuestion());

            int answerIndex = prompt.getUserInput(scanner);

            if (q.getOptions()[answerIndex - 1].equals(q.getAnswer())) {
                score += 20;
                continue;
            }
            score -= 10;
        }
        System.out.println(score);
        gameOn = false;
    }

    @Override
    public void run() {
        String[] options = {"Start"};
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage("PRESS 1 TO START... \n");

        int answerIndex = prompt.getUserInput(scanner);
        if (answerIndex == 1) {
            printWriter.println("GAME ON!\n");
            printWriter.flush();
        }
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


}



