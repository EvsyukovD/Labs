package com.example.lab4.dao;

import com.example.lab4.persons.Person;
import com.example.lab4.persons.Student;
import com.example.lab4.persons.Teacher;
import com.example.lab4.utils.Subject;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
@Service
public class JdbcService extends JdbcBase implements DAO {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/persons";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private final String INSERT_TEACHER = "INSERT INTO teachers(surname,name,patronymic,telnumber,year," +
            "subject,time_start,time_end)" +
            "VALUES (?,?,?,?,?,?,?,?)";
    private final String INSERT_STUDENT = "INSERT INTO students(surname,name,patronymic,telnumber,year," +
            "math,english,tenzors,informatics,physics,diffeqs)" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private final String SELECT_TEACHER = "SELECT * FROM teachers WHERE telnumber = ?";
    private final String SELECT_STUDENT = "SELECT * FROM students WHERE telnumber = ?";

    public JdbcService() {
        super(DB_URL, USER, PASSWORD);
    }

    public List<Person> getList() {
        List<Teacher> teachers = jdbcTemplate.query("SELECT * FROM teachers", new TeacherMapper());
        List<Student> students = jdbcTemplate.query("SELECT * FROM students", new StudentMapper());
        List<Person> persons = new ArrayList<>();
        persons.addAll(teachers);
        persons.addAll(students);
        teachers.clear();
        students.clear();
        return persons;
    }

    private Teacher findTeacher(long telnumber) {
        Teacher res = null;
        try {
            res = jdbcTemplate.queryForObject(SELECT_TEACHER, new Object[]{telnumber}, new TeacherMapper());
        } catch (Exception e) {
        }
        return res;
    }

    private Student findStudent(long telnumber) {
        Student res = null;
        try {
            res = jdbcTemplate.queryForObject(SELECT_STUDENT, new Object[]{telnumber}, new StudentMapper());
        } catch (Exception e) {
        }
        return res;
    }

    private boolean createTeacher(String data) {
        Teacher t = Teacher.createTeacher(data);
        if (t == null) {
            return false;
        }
        if (findTeacher(t.getNumber()) != null) {
            return false;
        }
        Object[] params = {t.getSurname(), t.getName(), t.getPatronymic(), t.getNumber(), t.getYear(),
                t.getSubject().toString(), t.getStart(), t.getFinish()};
        return jdbcTemplate.update(INSERT_TEACHER, params) > 0;
    }

    private boolean createStudent(String data) {
        Student t = Student.createStudent(data);
        if (t == null) {
            return false;
        }
        if (findStudent(t.getNumber()) != null) {
            return false;
        }
        Object[] params = new Object[11];
        Object[] mainInfo = {t.getSurname(), t.getName(), t.getPatronymic(), t.getNumber(), t.getYear()};
        Map<Subject, Integer> marks = t.getMarks();
        Subject[] values = Subject.values();
        for (int i = 0; i < mainInfo.length; i++) {
            params[i] = mainInfo[i];
        }
        for (int i = mainInfo.length; i < params.length; i++) {
            if (marks.containsKey(values[i - mainInfo.length])) {
                params[i] = marks.get(values[i - mainInfo.length]);
            } else {
                params[i] = 0;
            }
        }
        return jdbcTemplate.update(INSERT_STUDENT, params) > 0;
    }

    @Override
    public boolean createPerson(String id, String data) {
        if (id.equals("t")) {
            return createTeacher(data);
        }
        if (id.equals("s")) {
            return createStudent(data);
        }
        return false;
    }

    @Override
    public boolean deletePerson(long telNumber) {
        if (findTeacher(telNumber) != null) {
            jdbcTemplate.update("DELETE FROM teachers WHERE telnumber = ?", telNumber);
            return true;
        }
        if (findStudent(telNumber) != null) {
            jdbcTemplate.update("DELETE FROM students WHERE telnumber = ?", telNumber);
            return true;
        }
        return false;
    }

    protected Teacher updateTeacher(long oldTelNumber, String field, String data) {
        if (!checkParamsBeforeUpdate("t", field, data)) {
            return null;
        }
        if (!field.equals("telnumber")) {
            if (field.equals("surname") || field.equals("name") || field.equals("patronymic") || field.equals("subject")) {
                jdbcTemplate.update(
                        "UPDATE teachers SET " + field + " = ?\n", data);
            } else {
                jdbcTemplate.update(
                        "UPDATE teachers SET " + field + " = ?\n", Integer.valueOf(data));
            }

            return findTeacher(oldTelNumber);
        } else {
            long newTelNumber = Long.parseLong(data);
            jdbcTemplate.update(
                    "UPDATE teachers SET " + field + " = ?\n", data);
            return findTeacher(newTelNumber);
        }
    }

    protected Student updateStudent(long oldTelNumber, String field, String data) {
        if (!checkParamsBeforeUpdate("s", field, data)) {
            return null;
        }
        if (!field.equals("telnumber")) {
            if (field.equals("surname") || field.equals("name") || field.equals("patronymic")) {
                jdbcTemplate.update(
                        "UPDATE students SET " + field + " = ?\n", data);
            } else {
                jdbcTemplate.update(
                        "UPDATE students SET " + field + " = ?\n", Integer.valueOf(data));
            }
            return findStudent(oldTelNumber);
        } else {
            long newTelNumber = Long.parseLong(data);
            jdbcTemplate.update(
                    "UPDATE students SET " + field + " = ?\n", newTelNumber);
            return findStudent(newTelNumber);
        }
    }

    @Override
    public Person update(long telNumber, String field, String data) {
        Teacher t = findTeacher(telNumber);
        if (t != null) {
            return updateTeacher(telNumber, field, data);
        }
        Student s = findStudent(telNumber);
        if (s != null) {
            return updateStudent(telNumber, field, data);
        }
        return null;
    }

    @Override
    public Person find(long telNumber) {
        Person res;
        res = findTeacher(telNumber);
        if (res != null) {
            return res;
        }
        res = findStudent(telNumber);
        if (res != null) {
            return res;
        }
        return null;
    }

    private class TeacherMapper implements RowMapper<Teacher> {
        @Override
        public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
            //creating teacher
            Teacher t = new Teacher();
            t.setSurname(rs.getString("surname"));
            t.setName(rs.getString("name"));
            t.setPatronymic(rs.getString("patronymic"));
            try {
                t.setNumber(String.valueOf(rs.getLong("telnumber")));
                t.setYear(String.valueOf(rs.getInt("year")));
                t.setSubject(rs.getString("subject"));
                t.setStart(String.valueOf(rs.getInt("time_start")));
                t.setFinish(String.valueOf(rs.getInt("time_end")));
                return t;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class StudentMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            //creating student
            Student t = new Student();
            t.setSurname(rs.getString("surname"));
            t.setName(rs.getString("name"));
            t.setPatronymic(rs.getString("patronymic"));
            try {
                t.setNumber(String.valueOf(rs.getLong("telnumber")));
                t.setYear(String.valueOf(rs.getInt("year")));
                for (Subject subject : Subject.values()) {
                    String subj = subject.toString().toLowerCase(Locale.ROOT);
                    String mark = String.valueOf(rs.getInt(subj));
                    t.setMark(mark, subj);
                }
                return t;
            } catch (Exception e) {
            }
            return null;
        }
    }
}
