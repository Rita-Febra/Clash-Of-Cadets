package org.academiadecodigo.loopeytunes;

public class Question {

    private String question;
    private String answer;
    private String[] options;



    public Question(ListQuestions questionNumber){

        this.question = questionNumber.getQuestion();
        this.answer = questionNumber.getAnswer();
        options = new String[]{answer,"","",""};
    }


    public String[] getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }


    public void setOptions(String[] options) {
        this.options = options;
    }

    public void shuffleOptions(){}

}
