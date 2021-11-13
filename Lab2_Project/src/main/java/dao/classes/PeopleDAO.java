package dao.classes;

import utils.DataExceptions;
import persons.Student;
import persons.Teacher;

import java.io.*;

public class PeopleDAO implements DAO {
    private File folder;
    private File output;

    public PeopleDAO(File folder, File output) {
        this.folder = folder;
        this.output = output;
    }

    public boolean hasFile(String name) {
        File[] files = folder.listFiles();
        if (files == null) {
            return false;
        }
        for (File f : files) {
            if (f.isFile() && f.getName().equals(name + ".json")) {
                return true;
            }
        }
        return false;
    }

    public void out(String msg) {
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(output, true));
            buf.write(msg);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
        }
    }


    @Override
    public boolean update(String id, String field, String data) {
        return true;
    }

    @Override
    public boolean createPerson(String id, String data) throws IOException, DataExceptions {
        if (id.charAt(0) == 't') {
            Teacher teacher = new Teacher(data);
            teacher.writeToFile(new File(folder.getPath() + "\\t" + teacher.getName() + ".json"));
            out("createPerson " + id + " " + data + " -персона создана");
            return true;
        }
        if (id.charAt(0) == 's') {
            Student student = new Student(data);
            student.writeToFile(new File(folder.getPath() + "\\s" + student.getName() + ".json"));
            out("createPerson " + id + " " + data + " -персона создана");
            return true;
        }
        if (!id.startsWith("t") && !id.startsWith("s")) {
            out("createPerson " + id + " " + data + " -неправильный идентификатор");
            throw new DataExceptions(id + "-WrongTypeOfPerson");
        }
        return false;
        /*System.out.println("Какую персону создать:");
        String[] msgs = {"0.Учитель","1.Ученик"};
        String[] tFields = {"Фамилия","Имя","Отчество","Номер телефона","Год рождения","Предмет","Время начала","Время конца"};
        String[] sFields = {"Фамилия","Имя","Отчество","Номер телефона","Год рождения"};
        int choice = LabUtils.FunctionHelper.dialog(msgs,msgs.length,scan);
        StringBuilder builder = new StringBuilder();
        System.out.println("Введите поля:");
        String s;
        if(choice == 0){
           for(int i = 0;i < tFields.length;i++){
               System.out.println(tFields[i]);
               s = LabUtils.FunctionHelper.readLine(scan);
               builder.append(s);
               builder.append(";");
           }
            Persons.Teacher teacher;
           try {
               teacher = new Persons.Teacher(builder.toString());
           } catch(NumberFormatException | LabUtils.DataExceptions e){
               e.printStackTrace();
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
                builder.append(LabUtils.FunctionHelper.readLine(scan));
                builder.append(";");
            }
            System.out.println("Введите количество предметов:");
            int n = LabUtils.FunctionHelper.readInt(scan);
            for(int i = 0;i < n;i++){
                System.out.println("Предмет:");
                builder.append(LabUtils.FunctionHelper.readLine(scan));
                builder.append(";");
                System.out.println("Оценка:");
                builder.append(LabUtils.FunctionHelper.readLine(scan));
                builder.append(";");
            }
            Persons.Student student;
            try {
                student = new Persons.Student(builder.toString());
            } catch(NumberFormatException | LabUtils.DataExceptions e){
                e.printStackTrace();
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
            } catch (IOException | LabUtils.DataExceptions e){
                System.out.println("Ошибка при создании файла");
                return false;
            }
        }
        System.out.println("Объект создан");
        return true;*/
    }

    @Override
    public boolean deletePerson(String id) throws DataExceptions {
        if(hasFile(id)){
            File file = new File(folder.getPath() +"\\" + id + ".json");
            file.delete();
            out("deletePerson: " + id + " -персона удалена");
            return true;
        }
        out("deletePerson:" + id + " -персоны нет");
        throw new DataExceptions(id + "-PersonDoesNotExist");
    }

    @Override
    public boolean find(String id) {
        return true;
    }
}
