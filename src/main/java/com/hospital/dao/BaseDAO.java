package com.hospital.dao;

import java.util.List;

public interface BaseDAO<T> {
    void delete(String... ids);
    T get(String... ids);
    List<T> getAll();
    void save(T entity);
    void update(T entity);
}