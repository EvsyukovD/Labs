package com.example.lab4.utils;

import java.util.Locale;

public enum Subject {
    MATH,
    ENGLISH,
    TENZORS,
    INFORMATICS,
    PHYSICS,
    DIFFEQS;//ОДУ

    public static String[] toStrings() {
        String[] strings = new String[7];
        Subject[] subjects = Subject.values();
        for (int i = 0; i < subjects.length; i++) {
            strings[i] = subjects.toString();
        }
        return strings;
    }

    public static Subject value(String subject) throws DataExceptions {
        String str = subject.toUpperCase(Locale.ROOT);
        for (Subject s : Subject.values()) {
            if (s.toString().equals(str)) {
                return s;
            }
        }
        throw new DataExceptions("notASubject");
    }

    public static boolean isSubject(String subject) {
        String str = subject.toUpperCase(Locale.ROOT);
        for (Subject s : Subject.values()) {
            if (s.toString().equals(str)) {
                return true;
            }
        }
        return false;
    }
}
