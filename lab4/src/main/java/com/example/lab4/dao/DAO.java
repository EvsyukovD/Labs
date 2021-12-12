package com.example.lab4.dao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.example.lab4.utils.DataExceptions;
import com.example.lab4.persons.Person;

public interface DAO{
    boolean createPerson(String id,String data) throws IOException, DataExceptions;
    boolean deletePerson(String id) throws DataExceptions, IOException;
    Person update(String id,String field,String data) throws DataExceptions, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, InstantiationException;
    Person find(String id) throws IOException;
}
