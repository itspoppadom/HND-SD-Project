package com.hospital.dao;

import java.util.List;

import com.hospital.exceptions.DatabaseException;

public interface BaseDAO<T> {
    void delete(String... ids) throws DatabaseException;
    T get(String... ids) throws DatabaseException;
    List<T> getAll() throws DatabaseException;
    void save(T entity) throws DatabaseException;
    void update(T entity) throws DatabaseException;
}