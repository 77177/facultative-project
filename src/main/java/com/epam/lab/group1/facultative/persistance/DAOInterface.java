package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.FeedBack;

public interface DAOInterface<T> {

    T getById(int id);

    T create(T t);

    FeedBack update(T t);

    boolean deleteById(int id);
}
