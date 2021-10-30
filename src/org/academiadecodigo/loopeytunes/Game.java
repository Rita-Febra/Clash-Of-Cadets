package org.academiadecodigo.loopeytunes;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Game implements Runnable {

    private Question[] questionsP1 = new Question[ListQuestions.values().length / 2];
    private Question[] questionsP2 = new Question[ListQuestions.values().length / 2];
    private Player player1;
    private Player player2;
    private Thread threadPlayer1;
    private Thread threadPlayer2;
    PrintWriter printWriter;

    public Game(Socket playerSocket1, Socket playerSocket2) {

        try {
            printWriter = new PrintWriter(playerSocket1.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < ListQuestions.values().length; i++) {

            if (i % 2 == 0) {
                questionsP1[i / 2] = new Question(ListQuestions.values()[i]);
                continue;
            }
            questionsP2[(i - 1) / 2] = new Question(ListQuestions.values()[i]);
        }

        player1 = new Player(playerSocket1, questionsP1);
        player2 = new Player(playerSocket2, questionsP2);

    }

    public void startThreads() {
        threadPlayer1 = new Thread(player1);
        threadPlayer2 = new Thread(player2);
        threadPlayer1.start();
        threadPlayer2.start();
    }

    public void gameOver(){
        if (player1.getScore() == player2.getScore()) {
            printWriter.println("\n ## IT'S A TIE!");
            printWriter.flush();
            return;
        }

        Player winner = (player1.getScore() > player2.getScore() ? player1 : player2);
        Player looser = (player1.getScore() < player2.getScore() ? player1 : player2);

        winner.wins();
        looser.loses();

    }

    @Override
    public void run() {

        startThreads();

        //Thread.sleep
        while (!(player1.choicesAreMade() && player2.choicesAreMade())) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        player1.switchQuestions(questionsP2);
        player2.switchQuestions(questionsP1);

        //Thread.sleep
        while (player1.isGameOn() || player2.isGameOn()) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        gameOver();

    }

}
