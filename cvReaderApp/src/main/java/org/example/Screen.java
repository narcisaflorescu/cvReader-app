package org.example;

public class Screen {
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showOptions() {
        System.out.println("___________________________\n" +
                "Choose one option:\n" +
                "1: Insert a CV in the database\n" +
                "2: Read my CV from the database\n" +
                "3: Get list of CV\n" +
                "4: Get filtered list of CV\n" +
                "___________________________");
    }

    public void showFilters() {
        System.out.println("___________________\n" +
                "Choose one filter:\n" +
                "1: The users who are over 18. \n" +
                "2: The users who speek english\n" +
                "___________________");
    }
}
