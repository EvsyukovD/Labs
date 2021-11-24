package com.example.demo.persons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.utils.Subject;
import java.io.File;
import java.io.IOException;
import com.example.demo.utils.DataExceptions;
public class Teacher extends Person {
    private Subject subject;
    private int start;
    private int finish;

    @JsonCreator
    public Teacher(@JsonProperty("surname") String surname, @JsonProperty("name") String name,
                   @JsonProperty("patronymic") String patronymic,
                   @JsonProperty("number") long telNumber, @JsonProperty("year") int birthYear,
                   @JsonProperty("subject") Subject subject, @JsonProperty("start") int timeStart,
                   @JsonProperty("finish") int timeFinish) {
        this.subject = subject;
        this.year = birthYear;
        this.start = timeStart;
        this.finish = timeFinish;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.number = telNumber;
    }

    public Teacher(String teacher) throws NumberFormatException, DataExceptions {
        String[] fields = teacher.split(";");
        String msg = isCorrect(fields);
        if (!msg.equals("correct")) {
            throw new DataExceptions(msg);
        }
        this.surname = fields[0];
        this.name = fields[1];
        this.patronymic = fields[2];
        this.number = Long.parseLong(fields[3]);
        this.year = Integer.parseInt(fields[4]);
        this.subject = Subject.value(fields[5]);
        this.start = Integer.parseInt(fields[6]);
        this.finish = Integer.parseInt(fields[7]);
    }

    public Teacher(File file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Teacher t = objectMapper.readValue(file, Teacher.class);
        this.subject = t.subject;
        this.year = t.year;
        this.start = t.start;
        this.finish = t.finish;
        this.surname = t.surname;
        this.name = t.name;
        this.patronymic = t.patronymic;
        this.number = t.number;
    }

    public Subject getSubject() {
        return subject;
    }

    public int getStart() {
        return start;
    }

    private String isCorrect(String[] fields) throws NumberFormatException {
        if (fields.length < 8) {
            return "WrongData";
        }
        if (Long.parseLong(fields[3]) <= 0) {
            return fields[3] + "- WrongNumber";
        }
        if (Integer.parseInt(fields[4]) <= 0) {
            return fields[4] + "- WrongYear";
        }
        if (Integer.parseInt(fields[6]) <= 0) {
            return fields[6] + "- WrongStartTime";
        }
        if (Integer.parseInt(fields[7]) <= 0) {
            return fields[7] + "- WrongFinishTime";
        }
        return "correct";
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(String timeFinish) throws NumberFormatException, DataExceptions {
        if (Integer.parseInt(timeFinish) <= 0) {
            throw new DataExceptions("OutOfData");
        }
        this.finish = Integer.parseInt(timeFinish);
    }

    public void setStart(String timeStart) throws NumberFormatException, DataExceptions {
        if (Integer.parseInt(timeStart) <= 0) {
            throw new DataExceptions("OutOfData");
        }
        this.start = Integer.parseInt(timeStart);
    }

    public void setSubject(String subject) throws DataExceptions {
        this.subject = Subject.value(subject);
    }

    /*public void writeToFile(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, this);
    }*/

    @JsonIgnore
    public String getStringTeacher() {
        String res = this.surname + ";" + this.name + ";" + this.patronymic + ";" +
                (this.number) + ";" + this.year + ";";

        res += subject.toString() + ";" + this.start + ";" + this.finish + ";";

        return res;
    }
}
