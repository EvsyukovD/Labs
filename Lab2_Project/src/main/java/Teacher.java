import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Teacher extends Person{
    private Subject subject;
    private int timeStart;
    private int timeFinish;
   @JsonCreator
   public Teacher(@JsonProperty("surname") String surname,@JsonProperty("name") String name,
                  @JsonProperty("patronymic") String patronymic,
                  @JsonProperty("telNumber") long telNumber,@JsonProperty("birthYear") int birthYear,
                  @JsonProperty("subject") Subject subject,@JsonProperty("timeStart") int timeStart,
                  @JsonProperty("timeFinish") int timeFinish){
       this.subject = subject;
       this.birthYear = birthYear;
       this.timeStart = timeStart;
       this.timeFinish = timeFinish;
       this.surname = surname;
       this.name = name;
       this.patronymic = patronymic;
       this.telNumber = telNumber;
   }

   public Teacher(String teacher) throws NumberFormatException, DataExceptions {
          String [] fields = teacher.split(";");
       if(Long.parseLong(fields[3]) <= 0 || Integer.parseInt(fields[4]) <= 0 ||
               Integer.parseInt(fields[6]) <= 0 || Integer.parseInt(fields[7]) <= 0){
           throw new DataExceptions("OutOfData");
       }
          this.surname = fields[0];
          this.name = fields[1];
          this.patronymic = fields[2];
          /*if(Long.parseLong(fields[3]) <= 0 || Integer.parseInt(fields[4]) <= 0 ||
                  Integer.parseInt(fields[6]) <= 0 || Integer.parseInt(fields[7]) <= 0){
              throw new DataExceptions("OutOfData");
          }*/
          this.telNumber = Long.parseLong(fields[3]);
          this.birthYear = Integer.parseInt(fields[4]);
          this.subject = Subject.value(fields[5]);
          this.timeStart = Integer.parseInt(fields[6]);
          this.timeFinish = Integer.parseInt(fields[7]);
   }

    public Teacher(File file) throws IOException{

        ObjectMapper objectMapper = new ObjectMapper();
            Teacher t = objectMapper.readValue(file, Teacher.class);
            this.subject = t.subject;
            this.birthYear = t.birthYear;
            this.timeStart = t.timeStart;
            this.timeFinish = t.timeFinish;
            this.surname = t.surname;
            this.name = t.name;
            this.patronymic = t.patronymic;
            this.telNumber = t.telNumber;
    }
    public Subject getSubject(){
       return subject;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public int getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(String timeFinish) throws NumberFormatException,DataExceptions {
       if(Integer.parseInt(timeFinish) <= 0){
           throw new DataExceptions("OutOfData");
       }
        this.timeFinish = Integer.parseInt(timeFinish);
    }

    public void setTimeStart(String timeStart) throws NumberFormatException,DataExceptions{
        if(Integer.parseInt(timeStart) <= 0){
            throw new DataExceptions("OutOfData");
        }
        this.timeStart = Integer.parseInt(timeStart);
    }

    public void setSubject(String subject) throws DataExceptions {
        this.subject = Subject.value(subject);
    }

    public void writeToFile(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, this);
        /*BufferedWriter wr = new BufferedWriter(new FileWriter(file));
        wr.write(getStringTeacher());
        wr.newLine();
        wr.close();
*/
    }
    @JsonIgnore
    public String getStringTeacher(){
        String res = this.surname + ";" +this.name + ";"+ this.patronymic + ";" +
                (this.telNumber) + ";" + this.birthYear + ";";

        res += subject.toString() + ";" + this.timeStart + ";" + this.timeFinish + ";";

        return res;
        /*ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }*/
    }

}
