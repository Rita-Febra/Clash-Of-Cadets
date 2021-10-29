package org.academiadecodigo.loopeytunes;

public enum ListQuestions{


    QUESTION_1 ("De que cor é o ceu?", "Azul"),
    QUESTION_2 ("Que horas são?", " 00:00"),
    QUESTION_3 ("Esta tudo bem?", "sempre"),
    QUESTION_4 ("Vais embora?", "daqui a bocado"),
    QUESTION_5 ("Queres dormir?", "nao"),
    QUESTION_6 ("Vamos sair?","em dezembro"),
    QUESTION_7 ("Es bebedo?","as vezes"),
    QUESTION_8 ("Pizza com abacaxi?","nunca");

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
