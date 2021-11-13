package com.example.demo.dao.classes;

import com.example.demo.utils.DataExceptions;
import com.example.demo.persons.Person;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedList;

@Service
public class PeopleService implements DAO {

    private final File output = new File("C:\\Users\\devsy\\IdeaProjects\\demo\\PeopleService\\output.txt");
    private CashedPeopleDAO cDao;
    public PeopleService() {
        try {
            cDao = new CashedPeopleDAO(output);
            PrintWriter wr = new PrintWriter(output);
            wr.close();
        } catch (Exception e) {
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

    public LinkedList<Person> getList(){
           return cDao.getList();
    }

    @Override
    public boolean createPerson(String id, String data) throws DataExceptions {
        return cDao.createPerson(id, data);
    }

    @Override
    public boolean deletePerson(String id)  {
        //CashedPeopleDAO cDao = new CashedPeopleDAO( folder,output);
        try{
            return cDao.deletePerson(id);
        }catch (Exception e){
            return  false;
        }

    }

    @Override
    public Person update(String id, String field, String data){
        //CashedPeopleDAO cDao = new CashedPeopleDAO( folder,output);
        try{
            return cDao.update(id,field,data);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Person find(String id){
       // CashedPeopleDAO cDao = new CashedPeopleDAO(folder,output);
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
