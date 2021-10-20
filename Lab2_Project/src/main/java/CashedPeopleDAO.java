import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CashedPeopleDAO implements Dao{
    private  File folder;
    private  Scanner scan;
    private HashMap<String,String> map = new HashMap<>();
    public CashedPeopleDAO(Scanner scn,File fldr) throws IOException {
        folder = fldr;
        scan = scn;
        File[] files = folder.listFiles();
        for(File file : files){
            if(file.isFile() && file.getName().endsWith(".json")){
                if(file.getName().charAt(0) == 't'){
                   Teacher t  = new Teacher(file);
                   map.put("t" + t.getName(),t.getStringTeacher());
                }
                if(file.getName().charAt(0) == 's'){
                    Student s  = new Student(file);
                    map.put("s" + s.getName(),s.getStringStudent());
                }
            }
        }
    }
    /*public  boolean quit(){
        return false;
    }*/

    public boolean hasFile(String name){
        File[] files = folder.listFiles();
        if(files == null){
            return false;
        }
        for(File f : files){
            if(f.isFile() && f.getName().equals(name + ".json")){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean createPerson() {
        System.out.println("Какую персону создать:");
        String[] msgs = {"0.Учитель","1.Ученик"};
        String[] tFields = {"Фамилия","Имя","Отчество","Номер телефона","Год рождения","Предмет","Время начала","Время конца"};
        String[] sFields = {"Фамилия","Имя","Отчество","Номер телефона","Год рождения"};
        int choice = FunctionHelper.dialog(msgs,msgs.length,scan);
        StringBuilder builder = new StringBuilder();
        System.out.println("Введите поля:");
        if(choice == 0){
            for(int i = 0;i < tFields.length;i++){
                System.out.println(tFields[i]);
                builder.append(FunctionHelper.readLine(scan));
                builder.append(";");
            }
            Teacher teacher;
            try {
                teacher = new Teacher(builder.toString());
            } catch(NumberFormatException | DataExceptions e){
                System.out.println("Не удалось создать персону");
                return false;
            }
            File file = new File(folder.getPath() + "\\t" + teacher.getName() + ".json");
            if(hasFile("t" + teacher.getName())){
                file.delete();
                file = new File(folder.getPath() + "\\t" + teacher.getName() + ".json");
            }
            try {
                boolean newFile = file.createNewFile();
                if(!newFile){
                    System.out.println("Не удалось создать файл");
                    return false;
                }
                teacher.writeToFile(file);
                map.put("t" + teacher.getName(), teacher.getStringTeacher());
            } catch (IOException e){
                System.out.println("Ошибка при создании файла");
                return false;
            }
        } else{
            for(int i = 0;i <  sFields.length;i++){
                System.out.println(sFields[i]);
                builder.append(FunctionHelper.readLine(scan));
                builder.append(";");
            }
            System.out.println("Введите количество предметов:");
            int n = FunctionHelper.readInt(scan);
            for(int i = 0;i < n;i++){
                System.out.println("Предмет:");
                builder.append(FunctionHelper.readLine(scan));
                builder.append(";");
                System.out.println("Оценка:");
                builder.append(FunctionHelper.readLine(scan));
                builder.append(";");
            }
            Student student;
            try {
                student = new Student(builder.toString());
            } catch(NumberFormatException | DataExceptions e){
                System.out.println("Не удалось создать персону");
                return false;
            }
            File file = new File(folder.getPath() + "\\s" + student.getName() + ".json");
            if(hasFile("s" + student.getName())){
                file.delete();
                file = new File(folder.getPath() + "\\s" + student.getName() + ".json");
            }
            try {
                boolean newFile = file.createNewFile();
                if(!newFile){
                    System.out.println("Не удалось создать файл");
                    return false;
                }
                student.writeToFile(file);
                map.put("s" + student.getName(),student.getStringStudent());
            } catch (IOException | DataExceptions e){
                System.out.println("Ошибка при создании файла");
                return false;
            }
        }
        System.out.println("Объект создан");
        return true;
    }

    @Override
    public boolean deletePerson() {
        System.out.println("Кого вы хотите удалить:");
        String[] msgs = {"0.Учитель","1.Ученик"};
        int choice = FunctionHelper.dialog(msgs,msgs.length,scan);
        System.out.println("Укажите имя объекта");
        String name  = FunctionHelper.readLine(scan);
        char id = choice == 0 ? 't':'s';
        if(map.containsKey(id + name)){
            File file = new File(folder.getPath() + "\\" + id + name + ".json");
            boolean f = file.delete();
            if(f) {
                map.remove(id + name);
                System.out.println("Файл удалён");
                return true;
            } else {
                System.out.println("Ошибка при удалении файла");
                return false;
            }
        } else {
            System.out.println("Такой персоны нет");
            return false;
        }
        /*if(choice == 0){
            if(hasFile("t" + name)){
                File file = new File(folder.getPath() + "\\" + "t" + name + ".json");
                boolean f = file.delete();
                String info = f ? "Файл удалён":"Ошибка при удалении файла";
                System.out.println(info);
                return  f;
            } else {
                System.out.println("Такого файла нет");
                return false;
            }
        } else {
            if(hasFile("s" + name)){
                File file = new File(folder.getPath() + "\\" + "s" + name + ".json");
                boolean f = file.delete();
                String info = f ? "Файл удалён":"Ошибка при удалении файла";
                System.out.println(info);
                return f;
            } else {
                System.out.println("Такого файла нет");
                return false;
            }
        }*/
    }

    public void updateTeacher(Teacher teacher) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NumberFormatException, InstantiationException {
        String[] tFields = {"0.Фамилия","1.Имя","2.Отчество","3.Номер телефона","4.Год рождения","5.Предмет","6.Время начала","7.Время окончания",};
        String[] methodsT = {"setSurname","setName","setPatronymic","setTelNumber","setBirthYear","setSubject","setTimeStart","setTimeFinish"};
         String name = teacher.getName();
        System.out.println("Какое поле вы хотите обновить");
        int rc = FunctionHelper.dialog(tFields,tFields.length,scan);
        File file = new File(folder.getPath() + "\\" + "t" + name + ".json");
        //Teacher t = readPerson(name,true);
        System.out.println("Введите значение поля:");
        teacher.getClass().getMethod(methodsT[rc], String.class).invoke(teacher,FunctionHelper.readLine(scan));
        if(file.delete()) {
            File newFile = new File(folder.getPath() + "\\" + "t" + teacher.getName() + ".json");
            if(newFile.createNewFile()) {
                teacher.writeToFile(newFile);
                map.remove("t" + name);
                map.put("t" + name, teacher.getStringTeacher());
            } else {
                System.out.println("Не удалось создать файл");
            }
        } else {
            System.out.println("Не удалось удалить файл");
        }

    }

    public void updateStudent(Student student) throws IOException,NoSuchMethodException,InvocationTargetException,InstantiationException,IllegalAccessException, DataExceptions {
        String[] sFields = {"0.Фамилия", "1.Имя", "2.Отчество", "3.Номер телефона", "4.Год рождения", "5.Предмет и оценка",};
        String[] methodsS = {"setSurname", "setName", "setPatronymic", "setTelNumber", "setBirthYear", "setMarks"};
        String name = student.getName();
        System.out.println("Какое поле вы хотите обновить");
        int rc = FunctionHelper.dialog(sFields, sFields.length, scan);
        File file = new File(folder.getPath() + "\\" + "s" + name + ".json");
        //Student s = readPerson(name,false);
        if (rc != 5) {
            System.out.println("Введите значение поля:");
            student.getClass().getMethod(methodsS[rc], String.class).invoke(student,FunctionHelper.readLine(scan));
        } else {
            String[] msgs = {"0.Удалить предмет","1.Заменить оценку по предмету/добавить пердмет с оценкой"};
            int choice = FunctionHelper.dialog(msgs,msgs.length,scan);
            if(choice == 0){
                System.out.println("Введите предмет");
                String subject = FunctionHelper.readLine(scan);
                if(student.containsSubject(subject)){
                    student.removeSubject(subject);
                } else {
                    System.out.println("Такого предмета нет");
                    return;
                }
            } else {
                System.out.println("Введите предмет");
                String subject = FunctionHelper.readLine(scan);
                System.out.println("Введите оценку");
                String mark = FunctionHelper.readLine(scan);
                student.setMark(mark, subject);
            }
        }
        System.out.println("this");
        if(file.delete()) {
            File newfile = new File(folder.getPath() + "\\" + "s" + student.getName() + ".json");
            if(newfile.createNewFile()) {
                student.writeToFile(newfile);
                map.remove("s" + name);
                map.put("s" + name,student.getStringStudent());
            } else {
                System.out.println("Не удалось создать файл");
            }
        } else{
            System.out.println("Не удалось удалить файл");
        }
    }

    @Override
    public boolean update() {
        System.out.println("Кого вы хотите обновить:");
        String[] msgs = {"0.Учитель","1.Ученик"};
        int choice = FunctionHelper.dialog(msgs,msgs.length,scan);
        System.out.println("Укажите имя объекта");
        String name  = FunctionHelper.readLine(scan);
        char id = choice == 0 ? 't' : 's';
        System.out.println(map.keySet());
        if(map.containsKey(id + name)) {
            if (id == 't') {
                try {
                    Teacher teacher = new Teacher(map.get(id + name));
                    updateTeacher(teacher);
                } catch (DataExceptions | IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                    System.out.println("Не удалось обновить объект");
                    return false;
                }
            } else {
                try {
                    System.out.println(map.keySet());
                    Student student = new Student(map.get(id + name));
                    updateStudent(student);
                } catch (DataExceptions | IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                    System.out.println("Не удалось обновить объект");
                    return false;
                }
            }
            System.out.println("Объект обновлён");
        } else {
            System.out.println("Такой персоны нет");
        }
        return true;
    }

    @Override
    public boolean find() {
        System.out.println("Кого вы хотите найти:");
        String[] msgs = {"0.Учитель","1.Ученик"};
        int choice = FunctionHelper.dialog(msgs,msgs.length,scan);
        System.out.println("Укажите имя объекта");
        String name  = FunctionHelper.readLine(scan);
        char id = choice == 0 ? 't' : 's';
        if(map.containsKey(id + name)){
            ObjectMapper mapper = new ObjectMapper();
           if(id == 't'){
               try {
                   Teacher teacher = new Teacher(map.get(id + name));
                   System.out.println("Ваша персона : " + mapper.writeValueAsString(teacher));
               } catch(DataExceptions | JsonProcessingException e){
                   System.out.println("Ошибка при поиске персоны");
                   return false;
               }
           } else {
               try {
                   Student student = new Student(map.get(id + name));
                   System.out.println("Ваша персона :" + mapper.writeValueAsString(student));
               } catch(DataExceptions | JsonProcessingException e){
                   System.out.println("Ошибка при поиске персоны");
                   return false;
               }
           }
        } else{
            System.out.println("Такой персоны нет");
            return false;
        }
        return true;
    }
}
