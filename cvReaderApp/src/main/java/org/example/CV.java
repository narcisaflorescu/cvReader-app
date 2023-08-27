package org.example;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CV{

    String name;
    String workExperience;
    String language;
    int age;

    @Override
    public String toString() {
        return "Name: " + name + "; Work experience: " + workExperience +
                "; Language: " + language + "; Age: " + age + "\n";
    }

}
