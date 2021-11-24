package com.example.demo.utils;

import org.junit.jupiter.api.*;

class SubjectTest {
   static String correct;
   static String wrong;
    @BeforeAll
    static void setup(){
         correct = "math";
         wrong = "ath";
    }
    @Test
    void value() {
        try {
            Assertions.assertEquals(Subject.MATH, Subject.value(correct));
        }catch(Exception e){
            e.printStackTrace();
        }
        Assertions.assertThrows(DataExceptions.class, () -> {
                Subject.value(wrong);
        });
    }
    @Test
    void isSubject() {
        Assertions.assertTrue(Subject.isSubject(correct));
        Assertions.assertFalse(Subject.isSubject(wrong));
    }
}