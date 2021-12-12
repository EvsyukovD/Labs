package com.example.lab4.controller;

import com.example.lab4.dao.JdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    private JdbcService service;

    @Autowired
    public Controller(JdbcService service){
        this.service = service;
    }

    @GetMapping(value = "/persons/msgs")
    public ResponseEntity<String> getMsg(){
        String res = service.getList();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
