package com.example.demo.controllers;

import com.example.demo.dao.classes.PeopleService;
import com.example.demo.persons.Teacher;
import com.example.demo.utils.DataExceptions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class PersonControllerTest {

    @Test
    void createTeacher() throws DataExceptions {
        PeopleService service = Mockito.mock(PeopleService.class);
        Mockito.when(service.deletePerson(any())).thenReturn(true);

        PersonController personController = new PersonController(service);

        personController.createTeacher(new Teacher(""));

        Mockito.verify(service, Mockito.atLeast(3)).deletePerson(any());
    }
}