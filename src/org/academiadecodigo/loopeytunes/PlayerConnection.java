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

public class PlayerConnection implements Runnable {

    private Socket playerSocket;
    private Prompt prompt;
    private PrintWriter printWriter;

    private Question[] questions;
    private String[] jokers = {"50/50", "Phone", "Ask the audience"};

    private String playerName;
    private int score = 0;

    private boolean gameOn;
    private boolean choicesMade;

    public PlayerConnection(Socket playerSocket, Question[] questions) {

        this.playerSocket = playerSocket;
        this.questions = questions;
        try {
            printWriter = new PrintWriter(playerSocket.getOutputStream());
            prompt = new Prompt(playerSocket.getInputStream(), new PrintStream(playerSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // PUBLIC METHODS
    public boolean isGameOn() {
        return gameOn;
    }

    public boolean choicesAreMade() {
        return choicesMade;
    }

    public void switchQuestions(Question[] questions) {
        this.questions = questions;
        gameOn = true;
    }

    public int getScore() {
        return score;
    }

    // RUN METHODS
    private void menu() {
        String[] options = {"Start"};
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage("PRESS 1 TO START... \n");

        int answerIndex = prompt.getUserInput(scanner);
        if (answerIndex == 1) {
            printWriter.println("GAME ON!\n");
            printWriter.flush();
        }

    }

    private void username() {

        StringInputScanner username = new StringInputScanner();
        username.setMessage("Choose your name: ");

        playerName = prompt.getUserInput(username);
        printWriter.println("\nWELCOME TO CLASH OF CADETS " + playerName + "! \n" + "GOOD LUCK! \n");
        printWriter.flush();
    }

    private void chooseAnswers() {

        StringInputScanner scanner = new StringInputScanner();

        printWriter.println("Choose your answers wisely: \n");
        for (Question q : questions) {

            printWriter.println(q.getQuestion() + "\n" + "1: " + q.getAnswer());
            printWriter.flush();

            // INPUT ANSWERS 2, 3 AND 4
            for (int i = 2; i <= 4; i++) {

                scanner.setMessage(i + ": ");
                String message = (prompt.getUserInput(scanner));

                message = checkRepetition(message, scanner, q);

                q.setOption(true, message, i - 1);
            }
            q.shuffleAnswers();
        }

        printWriter.println("\n---You've completed your answers---");
        printWriter.flush();

    }

    private String checkRepetition(String message, StringInputScanner scanner, Question q) {

        List<String> optionsList = Arrays.asList(q.getOptionsToShuffle());

        while (optionsList.contains(message)) {
            printWriter.println("\nThis answer already exists. Please choose another one \n");
            printWriter.flush();
            message = prompt.getUserInput(scanner);

        }
        return message;
    }

    private void play() {

        printWriter.println("\n\n------READY-------\n\n-------SET--------\n\n--------GO--------\n");
        printWriter.flush();


        for (Question q : questions) {

            MenuInputScanner scanner = new MenuInputScanner(q.getOptions());
            scanner.setMessage(q.getQuestion());

            int answerIndex = prompt.getUserInput(scanner);


            if (q.getOptions()[answerIndex - 1].equals("Joker")) {
                answerIndex = jokerMenu(q);
            }

            updateScore(q, answerIndex);
        }

        printWriter.println("\nYour final score is: " + getScore());
        printWriter.flush();
        gameOn = false;
    }

    private int jokerMenu(Question q) {

        MenuInputScanner menuJoker = new MenuInputScanner(jokers);
        menuJoker.setMessage("Choose your Joker");
        int chosenIndex = prompt.getUserInput(menuJoker);

        if (jokers[chosenIndex - 1].equals("50/50")) {
            useFiftyFifty(q);
        }

        // DELETE CHOSEN JOKER
        jokers[chosenIndex - 1] = "";

        // DELETE JOKER OPTION
        q.setOption(false, "", q.getOptions().length - 1);

        //REPEAT QUESTION
        MenuInputScanner scanner = new MenuInputScanner(q.getOptions());
        scanner.setMessage(q.getQuestion());

        int answerIndex = prompt.getUserInput(scanner);
        return answerIndex;
    }

    private void useFiftyFifty(Question q) {

        int deletedOptions = 0;

        for (int i = 0; i < q.getOptions().length; i++) {

            if (q.getOptions()[i].equals(q.getAnswer())) {
                continue;
            }

            if (deletedOptions < 2) {
                q.setOption(false, "", i);
                deletedOptions++;
            }
        }
    }

    private void updateScore(Question q, int answerIndex) {

        if (q.getOptions()[answerIndex - 1].equals(q.getAnswer())) {
            score += 10;
            printWriter.println("\nCorrect Answer! 10 points for you.");
            printWriter.flush();

        } else {
            printWriter.println("\nWrong Answer! No points for you.");
            printWriter.flush();
        }

        printWriter.println("\nYour current score is: " + getScore());
        printWriter.flush();
    }

    // GAME OVER
    public void wins() {
        printWriter.println("\n## YOU HAVE WON THE GAME! CONGRATULATIONS ##\n");
        printWriter.flush();
    }

    public void loses() {
        printWriter.println("\n## SORRY BUT YOU HAVE LOST THE GAME, TRY AGAIN NEXT TIME ##\n");
        printWriter.flush();
    }

    public void tie() {
        try {
            printWriter = new PrintWriter(playerSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.println("\n## IT'S A TIE ##\n");
        printWriter.flush();
    }
    @Override
    public void run() {
        menu();
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



