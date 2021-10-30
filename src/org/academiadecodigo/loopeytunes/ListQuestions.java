package org.academiadecodigo.loopeytunes;

public enum ListQuestions{


    QUESTION_1 ("\nDe que cor é o ceu?", "Azul"),
    QUESTION_2 ("\nQue horas são?", "14"),
    QUESTION_3 ("\nEsta tudo bem?", "sempre"),
    QUESTION_4 ("\nVais embora?", "daqui a bocado"),
    QUESTION_5 ("\nQueres dormir?", "nao"),
    QUESTION_6 ("\nVamos sair?","em dezembro"),
    QUESTION_7 ("\nEs bebedo?","as vezes"),
    QUESTION_8 ("\nPizza com abacaxi?","nunca");

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
