package org.academiadecodigo.loopeytunes;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class Game implements Runnable {


    private LinkedList<Question> allQuestions = new LinkedList<>();
    private Question[] questionsP1 = new Question[5];
    private Question[] questionsP2 = new Question[5];
    private PlayerConnection playerConnection1;
    private PlayerConnection playerConnection2;
    private Thread threadPlayer1;
    private Thread threadPlayer2;
    private PrintWriter printWriterP1;
    private PrintWriter printWriterP2;

    public Game(Socket playerSocket1, Socket playerSocket2) {

        randomAllQuestions();
        playersQuestions();
        playerConnection1 = new PlayerConnection(playerSocket1, questionsP1);
        playerConnection2 = new PlayerConnection(playerSocket2, questionsP2);

    }

    public void randomAllQuestions() {
        int i = 0;
        while (i < 10) {
            if (allQuestions.isEmpty()) {
                allQuestions.add(new Question(ListQuestions.values()[(int) Math.floor(Math.random() * ListQuestions.values().length)]));
                i++;
                continue;
            }
            Question question = new Question(ListQuestions.values()[(int) Math.floor(Math.random() * ListQuestions.values().length)]);
            boolean hasQuestion = false;

            for (Question q : allQuestions) {

                if (q.getQuestion().equals(question.getQuestion())) {
                    hasQuestion = true;
                }
            }
            if (hasQuestion) {
                continue;
            }
            allQuestions.add(question);
            i++;

        }
        System.out.println(allQuestions.size());
    }

    public void playersQuestions() {
        for (int j = 0; j < allQuestions.size(); j++) {

            System.out.println(allQuestions.get(j).getQuestion());

            if (j % 2 == 0) {

                questionsP1[j / 2] = allQuestions.get(j);


            } else {

                questionsP2[(j - 1) / 2] = allQuestions.get(j);
            }
        }
    }

    public void startThreads() {
        threadPlayer1 = new Thread(playerConnection1);
        threadPlayer2 = new Thread(playerConnection2);
        threadPlayer1.start();
        threadPlayer2.start();
    }

    public void gameOver() {
        try {
            printWriterP1 = new PrintWriter(playerConnection1.getPlayerSocket().getOutputStream());
            printWriterP2 = new PrintWriter(playerConnection2.getPlayerSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (playerConnection1.getScore() == playerConnection2.getScore()) {
            printWriterP1.println("\n ## IT'S A TIE!");
            printWriterP1.flush();
            printWriterP2.println("\n ## IT'S A TIE!");
            printWriterP2.flush();
            return;
        }

        PlayerConnection winner = (playerConnection1.getScore() > playerConnection2.getScore() ? playerConnection1 : playerConnection2);
        PlayerConnection looser = (playerConnection1.getScore() < playerConnection2.getScore() ? playerConnection1 : playerConnection2);

        winner.wins();
        looser.loses();

    }

    @Override
    public void run() {

        startThreads();

        //Thread.sleep
        while (!(playerConnection1.choicesAreMade() && playerConnection2.choicesAreMade())) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        playerConnection1.switchQuestions(questionsP2);
        playerConnection2.switchQuestions(questionsP1);

        //Thread.sleep
        while (playerConnection1.isGameOn() || playerConnection2.isGameOn()) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        gameOver();

    }
}
