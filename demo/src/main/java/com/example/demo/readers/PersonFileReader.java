package com.example.demo.readers;

import com.example.demo.persons.Person;
import com.example.demo.persons.Student;
import com.example.demo.persons.Teacher;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class PersonFileReader {
    private static PersonFileReader reader;
    private File file;

    private PersonFileReader(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void changeFile(File file) {
        this.file = file;
    }

    public static PersonFileReader getInstance(File file) {
        if (reader == null) {
            reader = new PersonFileReader(file);
        }
        return reader;
    }

    public Person readFile() {
        try {
            if (file.getName().startsWith("t")) {
                Teacher teacher = new Teacher(file);
                return teacher;
            }
            if (file.getName().startsWith("s")) {
                Student student = new Student(file);
                return student;
            }
        } catch (IOException e) {
        }
        return null;
    }
}
