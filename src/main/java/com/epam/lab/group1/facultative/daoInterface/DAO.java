package com.epam.lab.group1.facultative.daoInterface;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> getById(int id);

    void deleteById(int id);

    List<T> getList();

    void create(T t);

    void update(T t);
}