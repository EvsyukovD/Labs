package com.example.demo.controllers;

import com.example.demo.dao.classes.PeopleService;
import com.example.demo.persons.Person;
import com.example.demo.persons.Student;
import com.example.demo.persons.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PersonController {

    private final PeopleService service;

    @Autowired
    public PersonController(PeopleService service) {
        this.service = service;
    }

    @PostMapping(value = "/people/create/teacher")
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        try {
            service.createPerson("t" + teacher.getName(), teacher.getStringTeacher());
            return new ResponseEntity<>(teacher, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/people/create/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
            service.createPerson("s" + student.getName(), student.getStringStudent());
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/people/find")
    public ResponseEntity<Person> find(@RequestParam String id) {
        Person person = service.find(id);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/people/delete")
    public ResponseEntity<String> delete(@RequestParam String id) {
        if (service.deletePerson(id)) {
            return new ResponseEntity<>("Персона удалена", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/people/update")
    public ResponseEntity<Person> update(@RequestParam String id, @RequestParam String field, @RequestParam String data) {
        Person person = service.update(id, field, data);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/people/list")
    public ResponseEntity<List<Person>> list() {
        return new ResponseEntity<>(service.getList(), HttpStatus.OK);
    }

    @PostMapping(value = "/people/file/static")
    public ResponseEntity<Person> readByStatic(@RequestParam String path) {
        Person person = service.readByStatic(path);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        }
    }
    @PostMapping(value = "/people/file/enum")
    public ResponseEntity<Person> readByEnum(@RequestParam String path) {
        Person person = service.readByEnum(path);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        }
    }
}
