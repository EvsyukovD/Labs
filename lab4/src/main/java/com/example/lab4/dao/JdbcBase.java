package com.example.lab4.dao;

import com.example.lab4.utils.FunctionHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcBase {
    private String DB_URL;
    private String USER;
    private String PASSWORD;
    protected JdbcTemplate jdbcTemplate;

    public JdbcBase(String url, String name, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, name, password);
        DB_URL = url;
        USER = name;
        PASSWORD = password;
        jdbcTemplate = new JdbcTemplate(dataSource);
        initTables();
    }


    public boolean teachersHaveField(String field) {
        String[] fields = {"subject", "year", "time_start", "time_end", "surname", "name", "patronymic", "telnumber"};
        for (String f : fields) {
            if (f.equals(field)) {
                return true;
            }
        }
        return false;
    }

    public boolean studentsHaveField(String field) {
        String[] fields = {"math", "english", "year", "surname", "name", "patronymic", "telnumber",
                "tenzors", "informatics", "physics", "diffeqs"};
        for (String f : fields) {
            if (f.equals(field)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkParamsBeforeUpdate(String id, String field, String data) {
        switch (id) {
            case "t":
                if (!teachersHaveField(field)) {
                    return false;
                }
                if (field.equals("time_end") || field.equals("time_start") || field.equals("year")) {
                    if (!FunctionHelper.isInt(data)) {
                        return false;
                    }
                    if (Integer.valueOf(data) < 0) {
                        return false;
                    }
                }
                if (field.equals("telnumber")) {
                    if (!FunctionHelper.isLong(data)) {
                        return false;
                    }
                    if (Long.parseLong(data) < 0) {
                        return false;
                    }
                }
                return true;
            case "s":
                if (!studentsHaveField(field)) {
                    return false;
                }
                if (!field.equals("name") && !field.equals("surname") &&
                        !field.equals("patronymic") && !field.equals("telnumber")) {
                    if (!FunctionHelper.isInt(data)) {
                        return false;
                    }
                    if (Integer.valueOf(data) < 0) {
                        return false;
                    }
                }
                if (field.equals("telnumber")) {
                    if (!FunctionHelper.isLong(data)) {
                        return false;
                    }
                    if (Long.parseLong(data) < 0) {
                        return false;
                    }
                }
                return true;
        }
        return false;
    }

    private void initTables() {
        initTeachersTables();
        initStudentsTables();
    }

    private void initTeachersTables() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS teachers\n" +
                "(\n" +
                "  surname  VARCHAR(200) NOT NULL ,\n" +
                "  name VARCHAR(200) NOT NULL ,\n" +
                "  patronymic VARCHAR(200) NOT NULL ,\n" +
                "  telnumber BIGINT CHECK(telnumber > 0) NOT NULL,\n" +
                "  year INTEGER CHECK(year > 0) NOT NULL,\n" +
                "  subject TEXT CHECK(subject IN ('MATH','ENGLISH','PROJECTS','TENZORS','INFORMATICS','PHYSICS','DIFFEQS'))NOT NULL,\n" +
                "  time_start INTEGER CHECK(time_start > 0) NOT NULL,\n" +
                "  time_end INTEGER CHECK(time_end > 0) NOT NULL,\n" +
                " PRIMARY KEY(telnumber)\n" +
                ");");
    }

    private void initStudentsTables() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS students\n" +
                "(\n" +
                "    surname  VARCHAR(200) NOT NULL ,\n" +
                "    name VARCHAR(200) NOT NULL ,\n" +
                "    patronymic VARCHAR(200) NOT NULL ,\n" +
                "    telnumber BIGINT CHECK(telnumber > 0) NOT NULL,\n" +
                "    year INTEGER CHECK(year > 0) NOT NULL,\n" +
                "    math INTEGER CHECK(math >= 0)NOT NULL,\n" +
                "    english INTEGER CHECK(english >= 0) NOT NULL,\n" +
                "    tenzors INTEGER CHECK(tenzors >= 0) NOT NULL,\n" +
                "    informatics INTEGER CHECK(informatics >= 0) NOT NULL,\n" +
                "    physics INTEGER CHECK(physics >= 0) NOT NULL,\n" +
                "    diffeqs INTEGER CHECK(diffeqs >= 0) NOT NULL,\n" +
                "PRIMARY KEY(telnumber)\n" +
                ");");
    }

}
