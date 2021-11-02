package org.academiadecodigo.loopeytunes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question {

    private String question;
    private String answer;
    private String[] options;
    private String[] optionsToShuffle; //Excluding Joker

    public Question(ListQuestions questionNumber) {

        this.question = questionNumber.getQuestion();
        this.answer = questionNumber.getAnswer();
        options = new String[]{answer, "", "", "","Joker"};
        optionsToShuffle = new String[]{answer, "", "", ""};
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String[] getOptions() {
        return options;
    }

    public String[] getOptionsToShuffle(){
        return optionsToShuffle;
    }

    public void setOption(boolean toShuffle, String answer, int index) {
        if(toShuffle){
            optionsToShuffle[index] = answer;
            return;
        }
        options[index] = answer;
    }

    public void shuffleAnswers(){

        List<String> optionsList = Arrays.asList(optionsToShuffle);

        Collections.shuffle(optionsList);
        optionsList.toArray(optionsToShuffle);

        for (int i=0; i< options.length-1 ; i++){
            options[i] = optionsToShuffle[i];
        }
    }

}

