package com.example.demo.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Random;

class FunctionHelperTest {
    static String i;
    static String l;
    static Random rand = new Random();
    @BeforeAll
     static void setup(){
        i = String.valueOf(rand.nextInt());
        l = String.valueOf(rand.nextLong());
    }
    @Test
    void isLong() {
        Assertions.assertTrue(FunctionHelper.isLong(l));
    }

    @Test
    void isInt() {
        Assertions.assertTrue(FunctionHelper.isInt(i));
    }
}