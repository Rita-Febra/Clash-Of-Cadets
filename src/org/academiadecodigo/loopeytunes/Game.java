package org.academiadecodigo.loopeytunes;


import java.net.Socket;
import java.util.LinkedList;

public class Game implements Runnable {


    private LinkedList<Question> allQuestions = new LinkedList<>();
    private Question[] questionsP1 = new Question[5];
    private Question[] questionsP2 = new Question[5];
    private Player player1;
    private Player player2;
    private Thread threadPlayer1;
    private Thread threadPlayer2;


    public Game(Socket playerSocket1, Socket playerSocket2) {

        randomAllQuestions();
        playersQuestions();
        player1 = new Player(playerSocket1, questionsP1);
        player2 = new Player(playerSocket2, questionsP2);

    }

    public void randomAllQuestions(){
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

                if (q.getQuestion() == question.getQuestion()) {
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

    public void playersQuestions(){
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
        threadPlayer1 = new Thread(player1);
        threadPlayer2 = new Thread(player2);
        threadPlayer1.start();
        threadPlayer2.start();
    }

    public void gameOver() {
        if (player1.getScore() == player2.getScore()) {
            System.out.println("It's a tie");
            return;
        }
        Player winner = (player1.getScore() > player2.getScore() ? player1 : player2);

        System.out.println(winner.getPlayerName());

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
