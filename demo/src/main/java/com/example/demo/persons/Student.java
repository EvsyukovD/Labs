package com.example.demo.persons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.demo.utils.Subject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import com.example.demo.utils.DataExceptions;
import com.example.demo.utils.FunctionHelper;

public class Student extends Person {
    protected HashMap<Subject, Integer> marks = new HashMap<>();

    @JsonCreator
    public Student(@JsonProperty("surname") String surname, @JsonProperty("name") String name, @JsonProperty("patronymic") String patronymic,
                   @JsonProperty("number") long telNumber, @JsonProperty("year") int birthYear, @JsonProperty("marks") HashMap<Subject, Integer> subjects) {
        this.year = birthYear;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.number = telNumber;
        marks.putAll(subjects);
    }

    public Student(String student) throws DataExceptions {
        String[] fields = student.split(";");
        /*if(!LabUtils.FunctionHelper.isInt(fields[3]) || !LabUtils.FunctionHelper.isInt(fields[4])){
            throw new LabUtils.DataExceptions("NotANumber");
        }
        for(int i = 5;i < fields.length - 1;i++){
            //if(!LabUtils.Subject.isSubject(fields[i].toUpperCase(Locale.ROOT)) || !LabUtils.FunctionHelper.isInt(fields[i + 1])){
                if(!LabUtils.Subject.isSubject(fields[i].toUpperCase(Locale.ROOT))){
                    throw new LabUtils.DataExceptions("NotASubject");
                }
                if(!LabUtils.FunctionHelper.isInt(fields[i + 1])){
                    throw new LabUtils.DataExceptions("NotANumber");
                }
            //}
            i = i + 1;
        }*/
        /*if(Long.parseLong(fields[3]) <= 0 || Integer.parseInt(fields[4]) <= 0){
            throw new LabUtils.DataExceptions("OutOfData");
        }*/
        String msg = isCorrect(fields);
        if (!msg.equals("correct")) {
            throw new DataExceptions(msg);
        }
        this.surname = fields[0];
        this.name = fields[1];
        this.patronymic = fields[2];
        this.number = Long.parseLong(fields[3]);
        this.year = Integer.parseInt(fields[4]);
        for (int i = 5; i < fields.length - 1; i++) {
            marks.put(Subject.value(fields[i].toUpperCase(Locale.ROOT)), Integer.parseInt(fields[i + 1]));
            i = i + 1;
        }
    }

    public Student(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        Student s = objectMapper.readValue(file, Student.class);
        this.year = s.year;
        this.surname = s.surname;
        this.name = s.name;
        this.patronymic = s.patronymic;
        this.number = s.number;
        marks.putAll(s.marks);
    }

    public void setMarks(HashMap<Subject, Integer> marks) {
        this.marks.putAll(marks);
    }

    public HashMap<Subject, Integer> getMarks() {
        return marks;
    }

    public int getMark(Subject subject) {
        return marks.get(subject);
    }


    public void setMark(String mark, String subject) throws DataExceptions, NumberFormatException {
        if (Integer.parseInt(mark) <= 0 || !Subject.isSubject(subject)) {
            throw new DataExceptions("WrongData");
        }
        marks.put(Subject.value(subject), Integer.parseInt(mark));
    }

    private String isCorrect(String[] fields) {
        if (fields.length < 7) {
            return "WrongData";
        }
        if (!FunctionHelper.isLong(fields[3])) {
            return fields[3] + "- NotATelNumber";
        }
        if (!FunctionHelper.isInt(fields[4])) {
            return fields[4] + "- NotAYear";
        }
        if (Long.parseLong(fields[3]) <= 0) {
            return fields[3] + "- NotATelNumber";
        }
        if (Long.parseLong(fields[4]) <= 0) {
            return fields[4] + "- NotAYear";
        }
        for (int i = 5; i < fields.length - 1; i++) {
            if (!Subject.isSubject(fields[i].toUpperCase(Locale.ROOT))) {
                return fields[i] + "- NotASubject";
            }
            if (!FunctionHelper.isInt(fields[i + 1])) {
                return fields[i + 1] + "- NotANumber";
            }
            i = i + 1;
        }
        return "correct";
    }

    /*public void writeToFile(File file) throws IOException, DataExceptions {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        Student s = new Student(this.getStringStudent());
        objectMapper.writeValue(new FileOutputStream(file), s);
    }*/

    @JsonIgnore
    public String getStringStudent() {
        String res = this.surname + ";" + this.name + ";" + this.patronymic + ";" +
                this.number + ";" + this.year + ";";
        for (Subject subject : marks.keySet()) {
            res = res + subject.toString() + ";" + marks.get(subject) + ";";
        }
        return res;
    }

    public void removeSubject(String subject) throws DataExceptions {
        marks.remove(Subject.value(subject));
    }

    public boolean containsSubject(String subject) throws DataExceptions {
        return marks.containsKey(Subject.value(subject));
    }
}
