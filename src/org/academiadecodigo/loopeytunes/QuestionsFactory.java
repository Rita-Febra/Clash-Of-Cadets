package org.academiadecodigo.loopeytunes;

import java.util.LinkedList;

public class QuestionsFactory {


    public static LinkedList<Question> makeListOfQuestions(int numberOfQuestions) {

        LinkedList<Question> allQuestions = new LinkedList<>();

        int i = 0;

        while (i < numberOfQuestions) {

            // Create first question
            if (allQuestions.isEmpty()) {
                allQuestions.add(new Question(ListQuestions.values()[(int) Math.floor(Math.random() * ListQuestions.values().length)]));
                i++;
                continue;
            }

            Question question = new Question(ListQuestions.values()[(int) Math.floor(Math.random() * ListQuestions.values().length)]);
            boolean hasQuestion = false;

            // Checking if question already exists in the list, if not, add
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
        return allQuestions;
    }
}
