package com.example.lab4.dao;

import com.example.lab4.persons.Person;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class JpaService implements CrudRepository<Person,Long> {
 private static final EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("ru.easyjava.data.jpa.hibernate");

 @Override
 public <S extends Person> S save(S entity) {
  return null;
 }

 @Override
 public <S extends Person> Iterable<S> saveAll(Iterable<S> entities) {
  return null;
 }

 @Override
 public Optional<Person> findById(Long aLong) {
  return Optional.empty();
 }

 @Override
 public boolean existsById(Long aLong) {
  return false;
 }

 @Override
 public Iterable<Person> findAll() {
  return null;
 }

 @Override
 public Iterable<Person> findAllById(Iterable<Long> longs) {
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
 public void delete(Person entity) {

 }

 @Override
 public void deleteAllById(Iterable<? extends Long> longs) {

 }

 @Override
 public void deleteAll(Iterable<? extends Person> entities) {

 }

 @Override
 public void deleteAll() {

 }
}
