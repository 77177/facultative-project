package com.epam.lab.group1.facultative.daoInterface;

import java.util.List;

public interface DAO<T> {

    T get(T t);

    T delete(T t);

    List<T> getList();

    T create(T t);

    T update(T t);
}