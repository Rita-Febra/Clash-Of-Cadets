package org.academiadecodigo.loopeytunes;

public enum ListQuestions {


    QUESTION_1("\nThe Design Pattern Decorator can also be called... \n", "Wrapper"),
    QUESTION_2("\nWhat method of an Exception object returns a message string?\n", "getMessage()"),
    QUESTION_3("\nWhat’s the charge difference between poles called?\n", "Voltage"),
    QUESTION_4("\nWhich instruction follows Decode on the CPU cycle?\n", "Execute"),
    QUESTION_5("\nWhat’s the most important difference between Digital and Analog signals?\n", "Noise interference"),
    QUESTION_6("\nWhat is the command used to switch branches?\n","Checkout"),
    QUESTION_7("\nWhat invention revolutionised electronic signal switching?\n", "Transistor"),
    QUESTION_8("\nHow do we write 8 in binary\n?", "1000"),
    QUESTION_9("\nWhich of the Access Modifiers is the safest?\n", "private"),
    QUESTION_10("\nThe Runnable interface contains which method:\n", "Run"),
    QUESTION_11("\nWhat’s a computer?\n", "It’s a reprogrammable machine"),
    QUESTION_12("\nWhat’s the keyword used to access the method or member variables of the superclass?\n", "Super"),
    QUESTION_13("\nI demand the same return type and signature. Who am I?\n", "Method overriding"),
    QUESTION_14("\nWhat guarantees type safety in a collection?\n", "Generics"),
    QUESTION_15("\nThe most used interfaces from the collection framework are?\n", "Map"),
    QUESTION_16("\nWhat synchronized() requires?\n", "Key"),
    QUESTION_17("\nWhich of the subnet masks has a bigger range of addresses?\n", "Class A"),
    QUESTION_18("\nWhat is the size of float variable in bits?\n", "32"),
    QUESTION_19("\nWhat is the default value of boolean variable?\n", "False"),
    QUESTION_20("\nWhich interface restricts duplicate elements?\n", "Set"),
    QUESTION_21("\nIn Java, everything is an...\n", "Object"),
    QUESTION_22("\nWhich of the OOP Pillars represents a Is-a relation?\n", "Inheritance"),
    QUESTION_23("\nWhich doesn't implement the collection interface?\n", "Map"),
    QUESTION_24("\nAn attempt to add the null key to a TreeSet will result in:\n", "NullPointerException"),
    QUESTION_25("\nWhich is the class that every classes in Java extends?)\n", "Object"),
    QUESTION_26("\nWho invented the Copy&Paste ?\n", "Xerox"),
    QUESTION_27("\nWhich of these persons has created the first algorithm to be processed by a machine?\n", "Ada Lovelace"),
    QUESTION_28("\nWho invented the first computer?\n", "Charles Babbage");


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
