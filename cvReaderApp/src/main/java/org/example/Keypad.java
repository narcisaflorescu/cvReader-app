package org.example;
import java.util.Scanner;

public class Keypad {
    Scanner myObj = new Scanner(System.in);

    public String getString() {
        String enter1 = myObj.nextLine();
        return myObj.nextLine();
    }

    public String getAnswer() {
        return myObj.next();
    }

    public int getInteger() {
        return myObj.nextInt();
    }
}
