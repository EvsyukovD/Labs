package com.example.demo.dao.classes;

import com.example.demo.persons.Student;
import com.example.demo.persons.Teacher;
import com.example.demo.readers.EnumPersonFileReader;
import com.example.demo.readers.PersonFileReader;
import com.example.demo.utils.DataExceptions;
import com.example.demo.persons.Person;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.List;

@Service
public class PeopleService implements DAO {

    private final File output = new File("C:\\Users\\devsy\\IdeaProjects\\demo\\PeopleService\\output.txt");
    private CashedPeopleDAO cDao;
    private EnumPersonFileReader enumReader = null;
    private PersonFileReader staticReader = null;
    public PeopleService() {
        try {
            cDao = new CashedPeopleDAO(output);
            PrintWriter wr = new PrintWriter(output);
            wr.close();
        } catch (Exception e) {
        }
    }

    public List<Person> getList() {
        return cDao.getList();
    }

    @Override
    public boolean createPerson(String id, String data) throws DataExceptions {
        return cDao.createPerson(id, data);
    }

    @Override
    public boolean deletePerson(String id) {
        try {
            return cDao.deletePerson(id);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Person update(String id, String field, String data) {
        try {
            return cDao.update(id, field, data);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Person find(String id) {
        return cDao.find(id);
    }

    private void toMap(Person person) {
        String id, data;
        if (person == null) {
            return;
        }
        if (person instanceof Teacher) {
            Teacher teacher = (Teacher) person;
            id = "t" + teacher.getName();
            data = teacher.getStringTeacher();
        } else {
            Student student = (Student) person;
            id = "s" + student.getName();
            data = student.getStringStudent();
        }
        try {
            createPerson(id, data);
        } catch (Exception e) {
        }
    }

    public Person readByStatic(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile() && file.getName().endsWith(".json")) {
            if (staticReader == null) {
                staticReader = PersonFileReader.getInstance(file);
            } else {
                staticReader.changeFile(file);
            }
            Person person = staticReader.readFile();
            toMap(person);
            return person;
        } else {
            return null;
        }
    }

    public Person readByEnum(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile() && file.getName().endsWith(".json")) {
            if (enumReader == null) {
                enumReader = EnumPersonFileReader.getEnumReader(file);
            } else {
                enumReader.changeFile(file);
            }
            Person person = enumReader.readFile();
            toMap(person);
            return person;
        } else {
            return null;
        }
    }
}
