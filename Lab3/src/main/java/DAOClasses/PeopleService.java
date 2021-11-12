package DAOClasses;

import LabUtils.DataExceptions;
import Persons.Person;
import Persons.Student;
import Persons.Teacher;
import Repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Optional;

//@Service
public class PeopleService implements DAO {
    private CashedPeopleDAO cDao;
    private final File output = new File("C:\\Users\\devsy\\IdeaProjects\\Lab3\\PeopleService\\output.txt");

    //private PersonRepository repository;

    public PeopleService(PersonRepository repository) {
        try {
            PrintWriter wr = new PrintWriter(output);
            wr.close();
            cDao = new CashedPeopleDAO(output);
        } catch (Exception e) {
        }
        //this.repository = repository;
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
/*
    public void add(File file){
        if(file == null){
            return;
        }
        if(file.getName().startsWith("t")){
            try {
                Teacher t = new Teacher(file);
                cDao.createPerson("t" + t.getName(),t.getStringTeacher());
                repository.save(t);
            } catch (Exception e){}
        }
        if(file.getName().startsWith("s")){
            try {
                Student s = new Student(file);
                cDao.createPerson("s" + s.getName(),s.getStringStudent());
                repository.save(s);
            } catch (Exception e){}
        }
    }

    public void deleteById(Long id){
        if(repository.findById(id).get() instanceof Teacher){
            Teacher t = (Teacher) repository.findById(id).get();
            try {
                cDao.deletePerson("t" + t.getName());
                repository.delete(t);
            } catch (Exception e){}
            return;
        }
        if(repository.findById(id).get() instanceof Student){
            Student s = (Student) repository.findById(id).get();
            try {
                cDao.deletePerson("s" + s.getName());
                repository.delete(s);
            } catch (Exception e){}
            return;
        }
    }
*/
    @Override
    public boolean createPerson(String id, String data) throws DataExceptions {
        //PeopleDAO pDao = new PeopleDAO(scn, folder,output);
        return cDao.createPerson(id, data);
    }

    @Override
    public boolean deletePerson(String id) throws DataExceptions {
        return cDao.deletePerson(id);
    }

    @Override
    public boolean update(String id, String field, String data) throws IOException, DataExceptions, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        //CashedPeopleDAO cDao = new CashedPeopleDAO(folder,output);
        return cDao.update(id, field, data);
    }

    @Override
    public boolean find(String id) {
        //CashedPeopleDAO cDao = new CashedPeopleDAO(folder,output);
        return cDao.find(id);
    }

    public static LinkedList<String> readManageFile(String path) throws IOException {
        File file = new File(path);
        LinkedList<String> strs = new LinkedList<>();
        String buffer = "";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (buffer != null) {
            buffer = reader.readLine();
            if (buffer != null) {
                strs.add(buffer);
            }
        }
        reader.close();
        return strs;
    }
}
