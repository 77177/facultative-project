package com.epam.lab.group1.facultative.daoImpl;

import com.epam.lab.group1.facultative.daoInterface.DAO;
import com.epam.lab.group1.facultative.model.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StudentDAO implements DAO<Student> {

    private JdbcTemplate jdbcTemplate;

    public StudentDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Student get(Student student) {
        student = jdbcTemplate.queryForObject("SELECT * FROM students where student_id ="
                        + student.getStudent_id()
                        + ";",
                new BeanPropertyRowMapper<>(Student.class));
        return student;
    }

    @Override
    public Student delete(Student student) {
        jdbcTemplate.execute("DELETE FROM students WHERE student_id =" + student.getStudent_id() + ";");
        return student;
    }

    @Override
    public List<Student> getList() {
        List<Student> studentList = jdbcTemplate.query("SELECT * FROM students", new BeanPropertyRowMapper<>(Student.class));
        return studentList;
    }

    @Override
    public Student create(Student student) {
        jdbcTemplate.update("INSERT INTO students (student_id, student_first_name, student_last_name, username, password)" +
                        " values(?,?,?,?,?);"
                , student.getStudent_id()
                , student.getStudent_first_name()
                , student.getStudent_last_name()
                , student.getUsername()
                , student.getPassword());
        return student;
    }

    @Override
    public Student update(Student student) {
        jdbcTemplate.update("UPDATE student SET " +
                        "students.student_first_name=?" +
                        ",students.student_last_name=?" +
                        ",students.username=?" +
                        ",students.password=?" +
                        " WHERE student_id = ?"
                , student.getStudent_first_name()
                , student.getStudent_last_name()
                , student.getUsername()
                , student.getPassword()
                , student.getStudent_id());
        return student;
    }
}
