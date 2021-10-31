package org.academiadecodigo.loopeytunes;

public class Question {

    private String question;
    private String answer;
    private String[] options;


    public Question(ListQuestions questionNumber) {

        this.question = questionNumber.getQuestion();
        this.answer = questionNumber.getAnswer();
        options = new String[]{answer, "", "", "","Joker"};
    }

    public String[] getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setOptions(String answer, int index) {
        options[index] = answer;
    }


    public String getQuestion() {
        return question;
    }

    public void shuffleOptions() {

    }
}

