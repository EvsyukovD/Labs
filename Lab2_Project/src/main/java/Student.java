import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class Student extends Person{
//private ArrayList<Subject> subjects = new ArrayList<Subject>();
protected HashMap<Subject,Integer> marks = new HashMap<>();
@JsonCreator
    public Student(@JsonProperty("surname") String surname, @JsonProperty("name") String name, @JsonProperty("patronymic") String patronymic,
                   @JsonProperty("telNumber") int telNumber, @JsonProperty("birthYear") int birthYear,@JsonProperty("marks") HashMap<Subject,Integer> subjects){
        this.birthYear = birthYear;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.telNumber = telNumber;
        marks.putAll(subjects);
    }

    public Student(String student) throws Subject.SubjectException{
        String [] fields = student.split(";");
        this.surname = fields[0];
        this.name = fields[1];
        this.patronymic = fields[2];
        this.telNumber = Integer.parseInt(fields[3]);
        this.birthYear = Integer.parseInt(fields[4]);
        for(int i = 5;i < fields.length - 1;i++){
            marks.put(Subject.value(fields[i].toUpperCase(Locale.ROOT)),Integer.parseInt(fields[i + 1]));
            i = i + 1;
        }
    }
    public Student(File file) throws IOException{
           ObjectMapper objectMapper = new ObjectMapper();
           objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
               Student s = objectMapper.readValue(file, Student.class);
               this.birthYear = s.birthYear;
               this.surname = s.surname;
               this.name = s.name;
               this.patronymic = s.patronymic;
               this.telNumber = s.telNumber;
               //this.marks = s.marks;
              marks.putAll(s.marks);
        //System.out.println(this.getStringStudent());

    }

    public void setMarks(HashMap<Subject,Integer> marks){
        this.marks.putAll(marks);
    }

    public HashMap<Subject,Integer> getMarks(){
       return marks;
    }

    public int getMark(Subject subject){
        return marks.get(subject);
    }


    public void setMark(String mark,String subject) throws Subject.SubjectException,NumberFormatException {
        marks.put(Subject.value(subject),Integer.parseInt(mark));
    }


    public void writeToFile(File file) throws IOException, Subject.SubjectException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        Student s = new Student(this.getStringStudent());

            objectMapper.writeValue(new FileOutputStream(file),s);
        //System.out.println(objectMapper.writeValueAsString(s));
        /*PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
        objectMapper.writeValue(out,marks);
        out.close();*/

        /*BufferedWriter wr = new BufferedWriter(new FileWriter(file));
        wr.write(getStringStudent());
        wr.newLine();
        wr.close();*/
    }
    @JsonIgnore
    public String getStringStudent(){
        String res = this.surname + ";" +this.name + ";"+ this.patronymic + ";" +
                this.telNumber + ";" + this.birthYear + ";";
        for(Subject subject : marks.keySet()){
            res = res + subject.toString() + ";" + marks.get(subject) + ";";
        }
         return res;
        /*ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }*/
    }
}
