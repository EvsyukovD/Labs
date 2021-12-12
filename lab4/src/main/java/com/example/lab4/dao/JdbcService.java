package com.example.lab4.dao;

import com.example.lab4.persons.Person;
import com.example.lab4.utils.DataExceptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;

@Service
public class JdbcService  extends JdbcBase implements DAO {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/persons";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    public JdbcService(){
        super(DB_URL,USER,PASSWORD);
    }

    public String getList(){
        ResultSet res;
        try {
            res = this.executeSqlStatement("INSERT INTO persons (surname,name) VALUES ('Pupkin','Vasya')");
            return res.getString("id");
        }catch(Exception e){}
        return null;
    }


    @Override
    public boolean createPerson(String id, String data) throws IOException, DataExceptions {
        return false;
    }

    @Override
    public boolean deletePerson(String id) throws DataExceptions, IOException {
        return false;
    }

    @Override
    public Person update(String id, String field, String data) throws DataExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, InstantiationException {
        return null;
    }

    @Override
    public Person find(String id) throws IOException {
        return null;
    }

}
