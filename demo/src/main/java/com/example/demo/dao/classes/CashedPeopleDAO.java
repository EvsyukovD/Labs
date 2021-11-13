package com.example.demo.dao.classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.example.demo.persons.*;
import com.example.demo.utils.*;

public class CashedPeopleDAO implements DAO {
    private Map<String, String> map = new HashMap<>();
    private File output;

    public CashedPeopleDAO(File output)  {
        this.output = output;
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

    public LinkedList<Person> getList(){
        LinkedList<Person> persons = new LinkedList<>();
        for(String id : map.keySet()){
            try {
                if (id.startsWith("t")) {
                    persons.add(new Teacher(map.get(id)));
                } else {
                    persons.add(new Student(map.get(id)));
                }
            } catch (Exception e){}
        }
        return persons;
    }

    @Override
    public boolean createPerson(String id, String data) throws DataExceptions {
        if (id.charAt(0) == 't') {
            Teacher teacher = new Teacher(data);
            //teacher.writeToFile(new File(folder.getPath() + "\\t" + teacher.getName() + ".json"));
            map.put("t" + teacher.getName(), teacher.getStringTeacher());
            out("createPerson " + id + " " + data + " -персона создана");
            return true;
        }
        if (id.charAt(0) == 's') {
            Student student = new Student(data);
            //student.writeToFile(new File(folder.getPath() + "\\s" + student.getName() + ".json"));
            map.put("s" + student.getName(),student.getStringStudent());
            out("createPerson " + id + " " + data + " -персона создана");
            return true;
        }
        if (!id.startsWith("t") && !id.startsWith("s")) {
            out("createPerson " + id + " " + data + " -неправильный идентификатор");
            throw new DataExceptions(id + "-WrongTypeOfPerson");
        }
        return false;
    }

    @Override
    public boolean deletePerson(String id) throws DataExceptions {
        if (map.containsKey(id)) {
            //File file = new File(folder.getPath() + "\\" + id + ".json");
            //file.delete();
            map.remove(id);
            out("deletePerson: " + id + " - персона удалена");
            return true;
        } else {
            out("deletePerson: " + id + " - такой персоны нет");
            throw new DataExceptions(id + "-PersonDoesNotExist");
        }
    }

    public void updateTeacher(Teacher teacher, String name, String data) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NumberFormatException, IOException {
        String id = "t" + teacher.getName();
        teacher.getClass().getMethod(name, String.class).
                invoke(teacher, data);
        map.remove(id);
        map.put("t" + teacher.getName(), teacher.getStringTeacher());
        //File file = new File(folder.getPath() + "\\t" + teacher.getName() + ".json");
        //.writeToFile(file);
    }

    public void updateStudent(Student student, String field, String name, String data) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, DataExceptions, IOException {
        String id = "s" + student.getName();
        if (field.equals("marks")) {
            String[] subs = data.split(":");
            student.setMark(subs[1], subs[0]);
        } else {
            student.getClass().getMethod(name, String.class).
                    invoke(student, data);
        }
        map.remove(id);
        map.put("s" + student.getName(), student.getStringStudent());
        //File file = new File(folder.getPath() + "\\s" + student.getName() + ".json");
        //student.writeToFile(file);
    }

    @Override
    public Person update(String id, String field, String data) throws DataExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, InstantiationException {
        if (map.containsKey(id)) {
            StringBuilder builder = new StringBuilder(field);
            builder.setCharAt(0, field.toUpperCase(Locale.ROOT).charAt(0));
            if (id.charAt(0) == 't') {
                Teacher teacher = new Teacher(map.get(id));
                updateTeacher(teacher, "set" + builder, data);
                out("update " + id + " " + field + " " + data + "- персона обновлена:");
                out(teacher.getStringTeacher());
                return new Teacher(map.get(id));
            }
            if (id.charAt(0) == 's') {
                Student student = new Student(map.get(id));
                updateStudent(student, field, "set" + builder, data);
                out("update " + id + " " + field + " " + data + "- персона обновлена:");
                out(student.getStringStudent());
                return new Student(map.get(id));
            }
        } else {
            out("update: " + id + " " + field + " " + data + "- такой персоны нет или ошибка в данных");
            throw new DataExceptions(id + " " + field + " " + data + "-PersonDoesNotExistOrErrorData");
        }
        return null;
    }

    @Override
    public Person find(String id) {
        if (map.containsKey(id)) {
            out("find " + id + ": " + map.get(id));
            if(id.startsWith("t")){
                try {
                    return new Teacher(map.get(id));
                } catch (Exception e){
                    return null;
                }
            } else {
                try {
                    return new Student(map.get(id));
                } catch (Exception e){
                    return null;
                }
            }
        } else {
            out("find " + id + " -такой персоны нет");
            return null;
        }
    }
}
