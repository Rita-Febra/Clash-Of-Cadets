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
    private String[] jokers = {"50/50", "Phone", "Ask the audience"};
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

    public int getScore() {
        return score;
    }

    public String getPlayerName() {
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
            String[] optionsToShuffle = new String[options.length - 1];

            //Exclusão do Joker
            for (int i = 0; i < (options.length - 1); i++) {
                optionsToShuffle[i] = options[i];
            }


            List<String> optionsList = Arrays.asList(optionsToShuffle);

            for (int i = 0; i < 3; i++) {

                chooseAnswer.setMessage((i + 2) + ": ");
                String message = (prompt.getUserInput(chooseAnswer));

                while (optionsList.contains(message)) {

                    printWriter.println("\nThis answer already exists. Please choose another one \n");
                    printWriter.flush();
                    message = prompt.getUserInput(chooseAnswer);

                }
                optionsToShuffle[i + 1] = message;

            }

            Collections.shuffle(optionsList);
            optionsList.toArray(optionsToShuffle);
            for (int i = 0; i < (options.length - 1); i++) {
                q.setOptions(optionsToShuffle[i], i);
            }
        }
        answersCompleted();

        printWriter.println("\n---You've completed your answers---");
        printWriter.flush();

    }

    public void answersCompleted() {
        completeAnswers = true;
    }

    private int jokerMenu(Question q) {
        MenuInputScanner menuJoker = new MenuInputScanner(jokers);
        menuJoker.setMessage("Choose your Joker");
        int jokerIndex = prompt.getUserInput(menuJoker);

        if (jokers[jokerIndex - 1].equals("50/50")) {

            int deletedOptions = 0;
            for (int i = 0; i < 4; i++) {
                if (deletedOptions < 2) {
                    if (!q.getOptions()[i].equals(q.getAnswer())) {
                        q.setOptions("", i);
                        deletedOptions++;
                    }
                }
            }


        }

        jokers[jokerIndex - 1] = "";
        q.getOptions()[q.getOptions().length - 1] = "";

        //q.setOptions("",q.getOptions().length-1);

        MenuInputScanner scanner = new MenuInputScanner(q.getOptions());
        scanner.setMessage(q.getQuestion());

        int answerIndex = prompt.getUserInput(scanner);
        return answerIndex;
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

    public Socket getSocket(){
        return playerSocket;
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



