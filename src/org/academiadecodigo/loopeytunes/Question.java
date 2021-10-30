package org.academiadecodigo.loopeytunes;

public class Question {

    private String question;
    private String response;
    private String[] options;



    public Question(ListQuestions questionNumber){

        this.question = questionNumber.getQuestion();
        this.response = questionNumber.getAnswer();
        options = new String[]{response,"","",""};
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String answer, int index) {
        options[index] = answer;
    }

    public String getResponse() {
        return response;
    }

    public String getQuestion() {
        return question;
    }
}
