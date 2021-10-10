import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class PeopleDAO implements Dao {
    private final File folder;
    private final Scanner scan;
    public PeopleDAO(Scanner scn){
       folder = new File("C:\\Users\\devsy\\IdeaProjects\\Lab2_Project\\Persons");
       scan = scn;
    }
    public  boolean quit(){
        return false;
    }

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

    /*public  boolean ObjectsToFile(ArrayList<Student> students,ArrayList<Teacher> teachers){
        if( students.size() == 0 && teachers.size() == 0){
            System.out.println("Нечего записывать");
            return false;
        }
        System.out.println("Введите имя файла");
        //Scanner scan = new Scanner(System.in);
        String s = FunctionHelper.readLine(scan);
        System.out.println(s);
        //scan.close();
        if(s == null){
            System.out.println("Ошибка ввода");
            return false;
        }

        //File file = new File(s);
        if(hasFile(s)){
            File file = new File(folder.getPath() + "\\" + s);
           try{
               FileWriter writer = new FileWriter(file);
               BufferedWriter wr = new BufferedWriter(writer);
               int ts =  teachers.size();
               int ss =  students.size();
               wr.write(ts);
               wr.newLine();
               wr.write(ss);
               wr.newLine();
               wr.close();
               writer.close();
               for(Teacher t : teachers){
                   t.writeToFile(file);
               }
               for(Student st : students){
                   st.writeToFile(file);
               }

           } catch (IOException e){
               System.out.println("Ошибка записи в файл");
               return false;
           }
        } else {
            System.out.println("Это не файл или файла в папке нет");
        }
        return true;
    }
*/
    public  boolean FileToObjects(ArrayList<Student> students,ArrayList<Teacher> teachers){
        System.out.println("Введите имя файла");
        String name = FunctionHelper.readLine(scan);
        System.out.println(name);
        if(name == null){
            System.out.println("Ошибка ввода");
            return false;
        }
        if(hasFile(name)){
            try{
               // FileReader reader = new FileReader(new File(folder.getPath() + "\\" + s));
                BufferedReader buffer = new BufferedReader(new FileReader(folder.getPath() + "\\" + name));
                int ts = Integer.parseInt(buffer.readLine());
                int ss = Integer.parseInt(buffer.readLine());
                if(ts < 0 || ss < 0){
                    System.out.println("Ошибка чтения из файла: отрицательное количество персон");
                    return false;
                }
                students.clear();
                teachers.clear();
                for(int i = 0;i < ts + ss && buffer.ready();i++){
                    try {
                        if (i < ts) {
                            teachers.add(new Teacher(buffer.readLine()));
                        } else {
                            students.add(new Student(buffer.readLine()));
                        }
                    } catch (NumberFormatException | Subject.SubjectException e){

                    }
                }
                buffer.close();
                System.out.println("Считанные объекты:");
                System.out.println("Учителя:");
                for(Teacher t : teachers){
                    System.out.println(t.getStringTeacher());
                }
                System.out.println("Ученики:");
                for(Student s : students){
                    System.out.println(s.getStringStudent());
                }
                return true;
            } catch (IOException e){
                System.out.println("Ошибка чтения из файла");
                return false;
            }
        }
        return true;
    }

    private <T extends Person> T readPerson(String name,boolean isTeacher) throws IOException {
        //ArrayList<T> person = new ArrayList<T>();
            //T res;
            // FileReader reader = new FileReader(new File(folder.getPath() + "\\" + s));
           //BufferedReader buffer = new BufferedReader(new FileReader(file));
                if(isTeacher){
                    return (T) new Teacher(new File(folder.getPath() + "\\" + "t" + name + ".json"));
                } else {
                    return  (T)(new Student(new File(folder.getPath() + "\\" + "s" + name + ".json")));
                }
                //buffer.close();
    }

    public void updateTeacher(String name) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NumberFormatException, InstantiationException {
        String[] tFields = {"0.Фамилия","1.Имя","2.Отчество","3.Номер телефона","4.Год рождения","5.Предмет","6.Время начала","7.Время окончания",};
        String[] methodsT = {"setSurname","setName","setPatronymic","setTelNumber","setBirthYear","setSubject","setTimeStart","setTimeFinish"};

        System.out.println("Какое поле вы хотите обновить");
        int rc = FunctionHelper.dialog(tFields,tFields.length,scan);
        File file = new File(folder.getPath() + "\\" + "t" + name + ".json");
            Teacher t = readPerson(name,true);
            System.out.println("Введите значение поля:");
            t.getClass().getMethod(methodsT[rc], String.class).invoke(t,FunctionHelper.readLine(scan));
            if(file.delete()) {
                File newFile = new File(folder.getPath() + "\\" + "t" + t.getName() + ".json");
                if(newFile.createNewFile()) {
                    t.writeToFile(newFile);
                } else {
                    System.out.println("Не удалось создать файл");
                }
            } else {
                System.out.println("Не удалось удалить файл");
            }

    }

    public void updateStudent(String name) throws IOException,NoSuchMethodException,InvocationTargetException,InstantiationException,IllegalAccessException, Subject.SubjectException {
        String[] sFields = {"0.Фамилия", "1.Имя", "2.Отчество", "3.Номер телефона", "4.Год рождения", "5.Предмет и оценка",};
        String[] methodsS = {"setSurname", "setName", "setPatronymic", "setTelNumber", "setBirthYear", "setMarks"};

        System.out.println("Какое поле вы хотите обновить");
        int rc = FunctionHelper.dialog(sFields, sFields.length, scan);
        File file = new File(folder.getPath() + "\\" + "s" + name + ".json");
        Student s = readPerson(name,false);
        if (rc != 5) {
            System.out.println("Введите значение поля:");
            s.getClass().getMethod(methodsS[rc], String.class).invoke(s,FunctionHelper.readLine(scan));
        } else {
            System.out.println("Введите предмет");
            String subject = FunctionHelper.readLine(scan);
            System.out.println("Введите оценку");
            String mark = FunctionHelper.readLine(scan);
            s.setMark(mark, subject);
        }
            if(file.delete()) {
                File newfile = new File(folder.getPath() + "\\" + "s" + s.getName() + ".json");
                if(newfile.createNewFile()) {
                    s.writeToFile(newfile);
                } else {
                    System.out.println("Не удалось создать файл");
                }
            } else{
                System.out.println("Не удалось удалить файл");
            }
    }
    @Override
    public boolean update(){
        System.out.println("Кого вы хотите обновить:");
        String[] msgs = {"0.Учитель","1.Ученик"};
        /*String[] tFields = {"0.Фамилия","1.Имя","2.Отчество","3.Номер телефона","4.Год рождения","5.Предмет","Время начала","Время окончания",};
        String[] methodsT = {"setSurname","setName","setPatronymic","setTelNumber","setBirthYear","setSubject","setTimeStart","setTimeFinish"};
        String[] sFields = {"0.Фамилия","1.Имя","2.Отчество","3.Номер телефона","4.Год рождения","5.Предмет и оценка",};
        String[] methodsS = {"setSurname","setName","setPatronymic","setTelNumber","setBirthYear","setMarks"};
*/
        int choice = FunctionHelper.dialog(msgs,msgs.length,scan);
        System.out.println("Укажите имя объекта");
        String name  = FunctionHelper.readLine(scan);
        if(hasFile("t" + name) || hasFile("s" + name)){
            if(choice == 0 && hasFile("t" + name)){
                try{
                    updateTeacher(name);
                }catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | NumberFormatException | InstantiationException e){
                    e.printStackTrace();
                    System.out.println("Ошибка чтения / ошибка ввода");
                }
            }
            if(choice == 1 && hasFile("s" + name)){
                try{
                    updateStudent(name);
                }catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | NumberFormatException | Subject.SubjectException | InstantiationException e){
                    e.printStackTrace();
                    System.out.println("Ошибка чтения / ошибка ввода");
                }

            }
        }else {
            System.out.println("Такого объекта нет");
            return false;
        }
        return true;
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
           } catch(NumberFormatException | Subject.SubjectException e){
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
            } catch(NumberFormatException | Subject.SubjectException e){
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
            } catch (IOException | Subject.SubjectException e){
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
        if(choice == 0){
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
        }

    }
    @Override
    public boolean find() {
        System.out.println("Кого вы хотите найти:");
        String[] msgs = {"0.Учитель","1.Ученик"};
        int choice = FunctionHelper.dialog(msgs,msgs.length,scan);
        System.out.println("Укажите имя объекта");
        String name  = FunctionHelper.readLine(scan);
        char id = choice == 0 ? 't' : 's';
        if(hasFile(id + name)){
            try {
                ObjectMapper mapper = new ObjectMapper();
                if (id == 't') {
                    mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
                    Teacher t = readPerson(name, true);
                    System.out.println("Ваша персона");
                    System.out.println(mapper.writeValueAsString(t));
                    return true;
                } else {
                    Student s = readPerson(name,false);
                    System.out.println("Ваша персона");
                    System.out.println(mapper.writeValueAsString(s));
                    return true;
                }
            } catch (IOException e){
                System.out.println("Ошибка чтения из файла");
                return false;
            }
        } else {
            System.out.println("Такого объекта нет");
            return false;
        }
    }
}
