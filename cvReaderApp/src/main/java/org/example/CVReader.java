package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.exceptions.NameException;

import java.util.ArrayList;
import java.util.List;


public class CVReader {

    private final Keypad keypad;
    private final Screen screen;
    private final CVDatabase cvDatabase;
    private CV currentCV =  new CV();

    public CVReader() {
        keypad = new Keypad();
        screen = new Screen();
        cvDatabase = new CVDatabase();
    }

    public void startActions() {
        while(true) {
            screen.showOptions();
            screen.showMessage("Insert an option from menu: ");
            int option = keypad.getInteger();

            if(!executeOption(option))
                return;
        }
    }



    private boolean executeOption(int option) {
        switch (option) {
            case 1 ->
                insertCV();
            case 2 -> {
                try {
                    readCV();
                } catch (NameException e) {
                    screen.showMessage(e.getMessage());
                }
            }
            case 3 -> getListOfCV();
            case 4 -> {
                screen.showFilters();
                int numberFilter = keypad.getInteger();
                getFilteredListOfCV(numberFilter);
            }
            default -> {
                try {
                    cvDatabase.updateDatabase();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                screen.showMessage("Invalid option");
                return false;
            }
        }

        return true;
    }

    private void getFilteredListOfCV(int noFilter) {
        for (CV cv : cvDatabase.getCvList()) {
            boolean isInFilter = false;

            //the current cv is displayed if checks the filter
            switch (noFilter) {
                case 1 -> isInFilter = cv.getAge() >= 18;
                case 2 -> isInFilter = cv.getLanguage().equals("english");
                default -> screen.showMessage("Invalid filter");
            }

            if (isInFilter)
                System.out.println(cv);
       }
    }


    private void insertCV() {
        currentCV = new CV();
        String answer = "No exception was caught";

        try {
            insertNameCV();
        }
        catch (NameException e) {
            screen.showMessage(e.getMessage());
            screen.showMessage("If you continue, the old CV will be removed. Do you want to continue? (Y/N)");
            answer = keypad.getAnswer();
        }
        finally {
            if(answer.equals("N"))
                return;

            if(answer.equals("Y"))
                removeCV();

            insertOtherDataCV();
            cvDatabase.getCvList().add(currentCV);

        }
    }

    private void insertNameCV() {
        screen.showMessage("Insert name");
        String name = keypad.getString();
        currentCV.setName(name);

        //check if the database contains a cv with this name
        for (CV cv: cvDatabase.getCvList()) {
            if (cv.getName().equals(name)) {
                throw new NameException("In databse is already a CV for this name");
            }
        }


    }

    private void removeCV() {
        for (CV cv: cvDatabase.getCvList()) {
            if (cv.getName().equals(currentCV.getName())) {
                cvDatabase.getCvList().remove(cv);
                return;
            }
        }
    }


    private void insertOtherDataCV() {
        ArrayList<String> availableData = new ArrayList<>(List.of("work experience", "language", "age"));
        String answer, newData;

        //user can set other relevant data about cv suggested by "availableData"
        for(String data : availableData) {
            screen.showMessage("Do you want to introduce " + data + "? (Y/N)");
            answer = keypad.getAnswer();
            if (answer.equals("Y")) {
                screen.showMessage("Insert " + data);
                newData = keypad.getString();
                setData(availableData.indexOf(data), newData);
            }
        }
    }


    private void setData(int index, String newData) {
        switch (index) {
            case 0 -> currentCV.setWorkExperience(newData);
            case 1 -> currentCV.setLanguage(newData);
            case 2 -> currentCV.setAge(Integer.parseInt(newData));
            default -> screen.showMessage("Invalid data");
        }
    }

    private void readCV() {
        screen.showMessage("Insert your name:");
        String username = keypad.getString();

        //check if the database contains a cv with this name
        for (CV cv: cvDatabase.getCvList()) {
            if (cv.getName().equals(username)) {
                System.out.println(cv);
                return;
            }
        }

        throw new NameException(username + " doesn't have a CV in database.");
    }


    private void getListOfCV() {
        for (CV cv: cvDatabase.getCvList()) {
            System.out.println(cv);
        }
    }


}
