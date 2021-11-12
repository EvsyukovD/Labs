package Persons;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Id;

import javax.persistence.Entity;
import java.io.File;

public class PersonFromFile {

    private String id;
    private String description;

    public PersonFromFile() {
    }

    public PersonFromFile(File file) {
        if (file.getName().startsWith("t")) {
            try {
                Teacher teacher = new Teacher(file);
                id = "t" + teacher.getName();
                description = teacher.getStringTeacher();
            } catch (Exception e) {
            }

        }
        if (file.getName().startsWith("s")) {
            try {
                Student student = new Student(file);
                id = "s" + student.getName();
                description = student.getStringStudent();
            } catch (Exception e) {
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}

