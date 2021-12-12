package com.example.lab4.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class JdbcBase implements Closeable {
    private Connection connection;
    private String DB_URL;
    private String USER;
    private String PASSWORD;
    protected Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private JdbcTemplate jdbcTemplate;
    public JdbcBase(String url, String name, String password) {
        try {
            DriverManagerDataSource dataSource  = new DriverManagerDataSource(url,name,password);
            //this.connection = DriverManager.getConnection(url,name,password);
            DB_URL = url;
            USER = name;
            PASSWORD = password;
            jdbcTemplate = new JdbcTemplate(dataSource);
            initTables();
            RowMapper<Object> r;
        } catch (Exception e) {
        }
    }

    public void execute(String sql){
        jdbcTemplate.execute(sql);
    }

    public <T> List<T> queryForObjects(String sql, Class<?> c, RowMapper<T> rowMapper){
        return jdbcTemplate.query(sql,rowMapper);
    }

    public <T> T queryForObject(String sql,Class<?> c,RowMapper<T> rowMapper) {
       return (T) jdbcTemplate.queryForObject(sql, c, rowMapper);
    }

    private void initTables(){
        initTeachersTables();
        initStudentsTables();
    }

    private void initTeachersTables(){
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS teachers\n" +
                "(\n" +
                "  id SERIAL PRIMARY KEY,\n" +
                "  surname  VARCHAR(200) NOT NULL ,\n" +
                "  name VARCHAR(200) NOT NULL ,\n" +
                "  patronymic VARCHAR(200) NOT NULL ,\n" +
                "  telnumber BIGINT CHECK(telnumber > 0) NOT NULL,\n" +
                "  year INTEGER CHECK(year > 0) NOT NULL\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS teachers_data\n" +
                "(\n" +
                "  teacher_id BIGINT PRIMARY KEY,\n" +
                "  subject TEXT CHECK(subject IN ('MATH','ENGLISH','PROJECTS','TENZORS','INFORMATICS','PHYSICS','DIFFEQS'))NOT NULL,\n" +
                "  time_start INTEGER CHECK(time_start > 0) NOT NULL,\n" +
                "  time_end INTEGER CHECK(time_end > 0) NOT NULL,\n" +
                "    CONSTRAINT fk_teachers FOREIGN KEY(teacher_id) REFERENCES teachers(id)\n" +
                ");");
    }

    private void initStudentsTables(){
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS students\n" +
                "(\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    surname  VARCHAR(200) NOT NULL ,\n" +
                "    name VARCHAR(200) NOT NULL ,\n" +
                "    patronymic VARCHAR(200) NOT NULL ,\n" +
                "    telnumber BIGINT CHECK(telnumber > 0) NOT NULL,\n" +
                "    year INTEGER CHECK(year > 0) NOT NULL\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS students_data\n" +
                "(\n" +
                "    student_id BIGINT PRIMARY KEY,\n" +
                "    math INTEGER CHECK(math >= 0)NOT NULL,\n" +
                "    english INTEGER CHECK(english >= 0) NOT NULL,\n" +
                "    tenzors INTEGER CHECK(tenzors >= 0) NOT NULL,\n" +
                "    informatics INTEGER CHECK(informatics >= 0) NOT NULL,\n" +
                "    physics INTEGER CHECK(physics >= 0) NOT NULL,\n" +
                "    diffeqs INTEGER CHECK(diffeqs >= 0) NOT NULL,\n" +
                "    CONSTRAINT fk_students FOREIGN KEY (student_id) REFERENCES students(id)\n" +
                ");");
    }

    private void reopenConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            }
        }catch (Exception e){}
    }

    public ResultSet executeSqlStatement(String sql) throws SQLException {
        reopenConnection();
        ResultSet res;
        Statement statement = connection.createStatement();
        res = statement.executeQuery(sql);
        statement.close();
        return res;
    }

    @Override
    public void close() throws IOException {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (Exception e) {
        }
    }
}
