package org.academiadecodigo.loopeytunes;

public enum ListQuestions {


    QUESTION_1("\nWhat are Wrapper Classes?\n", "Conversion of JAVA primitives into reference types."),
    QUESTION_2("\nWhat is the difference between equals() and == in JAVA?\n", "The equals() method compares the values of two objects, == refers to the memory address location."),
    QUESTION_3("\nWhat’s the charge difference between poles called?\n", "Voltage"),
    QUESTION_4("\nAnalog signals are…\n", "Continuous"),
    QUESTION_5("\nWhat’s the most important difference between Digital and Analog signals?\n", "Noise interference"),
    QUESTION_6("\nWhat’s the difference between a local and an instance variable?\n", "Instance variable is bound to the object, local is only used inside the scope where it was declared."),
    QUESTION_7("\nWhat invention revolutionised electronic signal switching?\n", "Transistor"),
    QUESTION_8("\nDifference between an Array and an ArrayList?\n", "A Array can’t contain different data type values."),
    QUESTION_9("\nWhat is Collections Class in JAVA?\n", "Framework that acts as an arquitecture for storing and manipulating a group of objects"),
    QUESTION_10("\nWhat is an interface in java?\n", "It’s a blueprint of a class that has a collection of abstract methods and static constants with empty bodies"),
    QUESTION_11("\nWhat’s a computer?\n", "It’s a reprogrammable machine."),
    QUESTION_12("\nWhat’s the keyword which is used to access the method or member variables of the superclass?\n", "Super"),
    QUESTION_13("\nWhen subclass declares a method that as the same type arguments has a method declared by its superclass it’s termed as:\n", "Method overriding"),
    QUESTION_14("\nWhat guarantees type safety in a collection?\n", "Generics"),
    QUESTION_15("\nThe most used interfaces from the collection framework are?\n", "Map"),
    QUESTION_16("\nWhich of those is synchronized?\n", "Vector"),
    QUESTION_17("\nWhich doesn’t permit to store a null value?\n", "TreeSet"),
    QUESTION_18("\nWhat is the size of float variable in bits?\n", "32"),
    QUESTION_19("\nWhat is the default value of Boolean variable?\n", "False"),
    QUESTION_20("\nWhich interface restricts duplicate elements?\n", "Set"),
    QUESTION_21("\nIn java arrays are?\n", "Objects"),
    QUESTION_22("\nIn an Iterator what implementation will traverse a collection in each direction?\n", "ListIterator"),
    QUESTION_23("\nWhich doesn’t implement the collection interface?\n", "Map"),
    QUESTION_24("\nThe default capacity of an ArrayList is:\n", "10");

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
