package dao.classes;

import utils.DataExceptions;
import persons.Student;
import persons.Teacher;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CashedPeopleDAO implements DAO {
    private final File folder;
    private Map<String, String> map = new HashMap<>();
    private File output;

    public CashedPeopleDAO(File folder, File output) throws IOException {
        this.folder = folder;
        this.output = output;
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                if (file.getName().charAt(0) == 't') {
                    Teacher t = new Teacher(file);
                    map.put("t" + t.getName(), t.getStringTeacher());
                }
                if (file.getName().charAt(0) == 's') {
                    Student s = new Student(file);
                    map.put("s" + s.getName(), s.getStringStudent());
                }
            }
        }
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
    public boolean createPerson(String id, String data) {
        return true;
    }

    @Override
    public boolean deletePerson(String id) throws DataExceptions {
        if (map.containsKey(id)) {
            File file = new File(folder.getPath() +"\\" + id + ".json");
            file.delete();
            map.remove(id);
            out("deletePerson: " + id + " - персона удалена");
            return true;
        } else {
            out("deletePerson: " + id + " - такой персоны нет");
            throw new DataExceptions(id + "-PersonDoesNotExist");
        }
    }

    public void updateTeacher(Teacher teacher, String name, String data) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NumberFormatException, InstantiationException, IOException {
        String id = "t" + teacher.getName();
        teacher.getClass().getMethod(name, String.class).
                invoke(teacher, data);
        map.remove(id);
        map.put("t" + teacher.getName(), teacher.getStringTeacher());
        File file = new File(folder.getPath() + "\\t" + teacher.getName() + ".json");
        teacher.writeToFile(file);
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
        File file = new File(folder.getPath() + "\\s" + student.getName() + ".json");
        student.writeToFile(file);
    }

    @Override
    public boolean update(String id, String field, String data) throws DataExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, InstantiationException {
        if (map.containsKey(id)) {
            StringBuilder builder = new StringBuilder(field);
            builder.setCharAt(0, field.toUpperCase(Locale.ROOT).charAt(0));
            if (id.charAt(0) == 't') {
                Teacher teacher = new Teacher(map.get(id));
                updateTeacher(teacher, "set" + builder, data);
                out("update " + id + " " + field + " " + data + "- персона обновлена:");
                out(teacher.getStringTeacher());
            }
            if (id.charAt(0) == 's') {
                Student student = new Student(map.get(id));
                updateStudent(student, field, "set" + builder, data);
                out("update " + id + " " + field + " " + data + "- персона обновлена:");
                out(student.getStringStudent());
            }
        } else {
            out("update: " + id + " "+ field + " " + data + "- такой персоны нет или ошибка в данных");
            throw new DataExceptions(id + " "+ field + " " + data +"-PersonDoesNotExistOrErrorData");
        }
        return true;
    }

    @Override
    public boolean find(String id) {
        if (map.containsKey(id)) {
            out("find " + id + ": " + map.get(id));
        } else {
            out("find " + id + " -такой персоны нет");
        }
        return map.containsKey(id);
    }
}
