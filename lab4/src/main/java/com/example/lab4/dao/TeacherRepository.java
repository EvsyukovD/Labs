package com.example.lab4.dao;

import com.example.lab4.persons.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class TeacherRepository implements CrudRepository<Teacher,Long> {
    @Override
    public <S extends Teacher> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Teacher> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Teacher> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Teacher> findAll() {
        return null;
    }

    @Override
    public Iterable<Teacher> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Teacher entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Teacher> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
