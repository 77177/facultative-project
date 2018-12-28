package com.epam.lab.group1.facultative.dao;

import com.epam.lab.group1.facultative.model.Teacher;

import java.util.Collections;
import java.util.List;

/**
 * Provides interaction between application and dataBase.
 */
public class TeacherDao {

    /**
     * @param teacherId teacher's id in the dataBase
     * @return a teacher that corresponds to  the teacherId
     */
    public Teacher findById(int teacherId) {
        return new Teacher();
    }

    /**
     * @return all teachers from the dataBase.
     */
    public List<Teacher> findAll() {
        return Collections.emptyList();
    }
}