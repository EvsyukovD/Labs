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
