package org.academiadecodigo.loopeytunes;

import java.net.Socket;
import java.util.LinkedList;

public class Game implements Runnable {

    private final int numberOfQuestions = 10;

    private LinkedList<Question> allQuestions;
    private Question[] questionsP1 = new Question[5];
    private Question[] questionsP2 = new Question[5];
    private PlayerConnection player1;
    private PlayerConnection player2;
    private Thread threadPlayer1;
    private Thread threadPlayer2;

    public Game(Socket playerSocket1, Socket playerSocket2) {

        allQuestions = QuestionsFactory.makeListOfQuestions(numberOfQuestions);
        playersQuestions();
        player1 = new PlayerConnection(playerSocket1, questionsP1);
        player2 = new PlayerConnection(playerSocket2, questionsP2);

    }

    // Giving players questions
    public void playersQuestions() {

        for (int j = 0; j < allQuestions.size(); j++) {

            if (j % 2 == 0) {
                questionsP1[j / 2] = allQuestions.get(j);
                continue;
            }
            questionsP2[(j - 1) / 2] = allQuestions.get(j);

        }
    }

    public void startThreads() {

        threadPlayer1 = new Thread(player1);
        threadPlayer2 = new Thread(player2);
        threadPlayer1.start();
        threadPlayer2.start();
    }

    public void gameOver() {

        if (player1.getScore() == player2.getScore()) {
            player1.tie();
            player2.tie();
            return;
        }

        PlayerConnection winner = (player1.getScore() > player2.getScore() ? player1 : player2);
        PlayerConnection looser = (player1.getScore() < player2.getScore() ? player1 : player2);

        winner.wins();
        looser.loses();

    }

    @Override
    public void run() {

        startThreads();

        while (!(player1.choicesAreMade() && player2.choicesAreMade())) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        player1.switchQuestions(questionsP2);
        player2.switchQuestions(questionsP1);

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
