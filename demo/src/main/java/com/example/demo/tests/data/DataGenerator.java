package com.example.demo.tests.data;

import java.util.Random;

public class DataGenerator {
    //private static String[] names = {"Donald","John"};
    public static final String teacherWrongData = "Trump;Donald;Johnatan;94574585;-2000;math;10;12;";
    public static final String studentWrongData = "Trump;John;Johnatan;94574585;-2000;math;4;";
    public static final String teacherCorrectData = "Trump;Donald;Johnatan;94074585;2000;math;10;12;";
    public static final String studentCorrectData = "Trump;John;Johnatan;94074585;2000;math;4;";
    private static final Random rand = new Random();

    public static String randomInt(){
        return String.valueOf(rand.nextInt());
    }

    public static String randomLong(){
        return String.valueOf(rand.nextLong());
    }

    public static String getPersonData(DataType.Persons type,boolean isWrong){
        switch(type){
            case STUDENT:
                if(isWrong){
                    return "sDonald " + studentWrongData;
                } else {
                    return "sDonald " + studentCorrectData;
                }
            case TEACHER:
                if(isWrong){
                    return "tDonald " + teacherWrongData;
                } else {
                    return "tDonald " + teacherCorrectData;
                }
        }
        return null;
    }

}
