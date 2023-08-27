package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Paths;

public class CVDatabase {

    @Getter
    public ArrayList<CV> cvList = new ArrayList<>();

    public CVDatabase()  {/*
        cvList.add(new CV("Florescu", "ING", "english", 25));
        cvList.add(new CV("Popescu", "EY", "french", 30));
        cvList.add(new CV("Renta", "Engie", "french", 47));
        */
        loadDatabase();
    }

    private void loadDatabase() {

        try {
            String data = new String(Files.readAllBytes(Paths.get("C:\\Users\\ABC\\IdeaProjects\\cvReaderApp\\src\\cvReader.json")));
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {

                String str = jsonArray.get(i).toString();
                JSONObject object1 = new JSONObject(str);

                String name = object1.getString("name");
                String workExperience = object1.getString("workExperience");
                String language = object1.getString("language");
                int age = object1.getInt("age");

                CV cv = new CV(name, workExperience, language, age);
                cvList.add(cv);

            }
        }
        catch (IOException e) {
                e.printStackTrace();
            }
    }


    public void updateDatabase() throws JsonProcessingException {

        //Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(cvList);

        try (FileWriter file = new FileWriter("C:\\Users\\ABC\\IdeaProjects\\cvReaderApp\\src\\cvReader.json")) {
            file.write(String.valueOf(jsonString));
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }







}




