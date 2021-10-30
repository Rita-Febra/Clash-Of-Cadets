package org.academiadecodigo.loopeytunes;


import java.net.Socket;

public class Game implements Runnable {

    private Question[] questionsP1 = new Question[ListQuestions.values().length / 2];
    private Question[] questionsP2 = new Question[ListQuestions.values().length / 2];
    private Player player1;
    private Player player2;
    private Thread threadPlayer1;
    private Thread threadPlayer2;

    public Game(Socket playerSocket1, Socket playerSocket2) {

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


    @Override
    public void run() {


        threadPlayer1 = new Thread(player1);
        threadPlayer2 = new Thread(player2);

        threadPlayer1.start();
        threadPlayer2.start();


        while(!(player2.getCompleteAnswers() && player1.getCompleteAnswers())){


        }


        }
    }

