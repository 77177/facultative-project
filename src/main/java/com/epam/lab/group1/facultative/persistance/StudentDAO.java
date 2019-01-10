package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.Student;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDAO implements DAOInterface<Student> {

    private JdbcTemplate jdbcTemplate;

    public StudentDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Student> getById(int id) {
        Optional<Student> student = Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM students WHERE student_id ="
                        + id
                        + ";",
                new BeanPropertyRowMapper<>(Student.class)));
        return student;
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.execute("DELETE FROM students WHERE student_id =" + id + ";");
    }

    @Override
    public List<Student> getList() {
        List<Student> studentList = jdbcTemplate.query("SELECT * FROM students", new BeanPropertyRowMapper<>(Student.class));
        return studentList;
    }

    @Override
    public Student create(Student student) {
        jdbcTemplate.update("INSERT INTO students (student_first_name, student_last_name, username, password)" +
                        " VALUES(?,?,?,?);"
                , student.getStudentFirstName()
                , student.getStudentLastName()
                , student.getUsername()
                , student.getPassword());
        student = jdbcTemplate.queryForObject("SELECT * FROM students WHERE username = ?;"
                , new BeanPropertyRowMapper<>(Student.class), student.getUsername());
        return student;
    }

    @Override
    public void update(Student student) {
        jdbcTemplate.update("UPDATE student SET " +
                        "students.student_first_name=?" +
                        ",students.student_last_name=?" +
                        ",students.username=?" +
                        ",students.password=?" +
                        " WHERE student_id = ?"
                , student.getStudentFirstName()
                , student.getStudentLastName()
                , student.getUsername()
                , student.getPassword()
                , student.getStudentId());
    }
}
