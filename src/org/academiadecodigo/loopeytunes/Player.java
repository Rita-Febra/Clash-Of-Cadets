package org.academiadecodigo.loopeytunes;


import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable {

    private Socket playerSocket;
    private PrintWriter printWriter;
    private Prompt prompt;

    private Question[] questions;
    private String playerName;
    private int score = 0;

    private boolean gameOn;
    private boolean choicesMade;


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
        printWriter.println("Welcome to Clash of Cadets " + playerName + "! \n" + "Good luck! \n");
        printWriter.flush();
    }

    private void chooseAnswers() {
        for (Question q : questions) {
            printWriter.println(q.getQuestion() + "\n 1: " + q.getAnswer());
            printWriter.flush();
            String[] options = q.getOptions();
            for (int i = 0; i < 3; i++) {
                StringInputScanner chooseAnswer = new StringInputScanner();
                chooseAnswer.setMessage((i + 2) + ": ");
                options[i + 1] = prompt.getUserInput(chooseAnswer);
                System.out.println(options[i + 1]);
            }

            q.setOptions(options);
        }
    }

    private void play() {
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


