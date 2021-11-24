package com.example.demo.readers;

import com.example.demo.persons.Person;
import com.example.demo.persons.Student;
import com.example.demo.persons.Teacher;

import java.io.File;
import java.io.IOException;

public enum EnumPersonFileReader {
    ENUM_READER;
    private File file;

    public static EnumPersonFileReader getEnumReader(File file) {
        if (ENUM_READER.file == null) {
            ENUM_READER.file = file;
        }
        return ENUM_READER;
    }

    public File getFile() {
        return file;
    }

    public void changeFile(File file) {
        ENUM_READER.file = file;
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
