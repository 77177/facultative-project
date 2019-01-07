package com.epam.lab.group1.facultative.daoImpl;

import com.epam.lab.group1.facultative.daoInterface.DAO;
import com.epam.lab.group1.facultative.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
public class StudentDAO implements DAO<Student> {

    private JdbcTemplate jdbcTemplate;

    public StudentDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Student get(Student student) {
        return null;
    }

    @Override
    public Student delete(Student student) {
        return null;
    }

    @Override
    public List<Student> getList() {
        return null;
    }

    @Override
    public Student create(Student student) {
        return null;
    }

    @Override
    public Student update(Student student) {
        return null;
    }
}
