package org.academiadecodigo.loopeytunes;

public enum ListQuestions{


    QUESTION_1 ("\nDe que cor é o ceu?\n", "Azul"),
    QUESTION_2 ("\nQue horas são?\n", "14"),
    QUESTION_3 ("\nEsta tudo bem?\n", "sempre"),
    QUESTION_4 ("\nVais embora?\n", "daqui a bocado"),
    QUESTION_5 ("\nQueres dormir?\n", "nao"),
    QUESTION_6 ("\nVamos sair?\n","em dezembro"),
    QUESTION_7 ("\nEs bebedo?\n","as vezes"),
    QUESTION_8 ("\nPizza com abacaxi?\n","nunca");

    private String question;
    private String answer;

    ListQuestions(String question, String answer) {
        this.question = question;
        this.answer = answer;

    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
}
