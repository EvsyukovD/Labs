package com.example.lab4.controller;

import com.example.lab4.dao.JdbcService;
import com.example.lab4.persons.Person;
import com.example.lab4.persons.Student;
import com.example.lab4.persons.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {


    private JdbcService service;

    @Autowired
    public Controller(JdbcService service) {
        this.service = service;
    }

    @GetMapping(value = "/persons/list")
    public ResponseEntity<List<Person>> getList() {
        List<Person> res = service.getList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value = "/persons/create/create_teacher")
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        boolean res = service.createPerson("t", teacher.getStringTeacher());
        if (res) {
            return new ResponseEntity<>(teacher, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/persons/create/create_student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        boolean res = service.createPerson("s", student.getStringStudent());
        if (res) {
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/persons/delete")
    public ResponseEntity<String> delete(@RequestParam long telnumber) {
        boolean res = service.deletePerson(telnumber);
        if (res) {
            return new ResponseEntity<>("Персона удалена", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/persons/update")
    public ResponseEntity<Person> update(@RequestParam long telnumber, @RequestParam String field, @RequestParam String data) {
        Person res = service.update(telnumber, field, data);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
