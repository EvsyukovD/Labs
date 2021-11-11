package com.example.demo.DAOClasses;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.LabUtils.DataExceptions;
import com.example.demo.Persons.Person;

public interface DAO {
    boolean createPerson(String id,String data) throws IOException, DataExceptions;
    boolean deletePerson(String id) throws DataExceptions, IOException;
    Person update(String id,String field,String data) throws DataExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, InstantiationException;
    Person find(String id) throws IOException;
}
