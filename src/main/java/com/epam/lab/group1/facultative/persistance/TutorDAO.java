package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.Tutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TutorDAO implements DAOInterface<Tutor> {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String sql;

    public TutorDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Tutor> getById(int id) {
        sql = "SELECT * FROM tutors WHERE tutor_id = :id;";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, new BeanPropertyRowMapper<>(Tutor.class)));
    }

    @Override
    public void deleteById(int id) {
        sql = "SELECT * FROM tutors WHERE tutor_id = :id;";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    @Override
    public List<Tutor> getList() {
        sql = "SELECT * FROM tutor";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tutor.class));
    }

    @Override
    public Tutor create(Tutor tutor) {
        sql = "INSERT INTO tutors (tutor_first_name, tutor_last_name, username, password) VALUES(:tutorFirstName,:tutorLastName,:username,:password);";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tutorFirstName", tutor.getTutorFirstName());
        sqlParameterSource.addValue("tutorLastName", tutor.getTutorLastName());
        sqlParameterSource.addValue("username", tutor.getUsername());
        sqlParameterSource.addValue("password", tutor.getPassword());
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);

        tutor = jdbcTemplate.queryForObject("SELECT * FROM tutors WHERE username = ?;"
                , new BeanPropertyRowMapper<>(Tutor.class), tutor.getUsername());
        return tutor;
    }

    @Override
    public void update(Tutor tutor) {
        sql = "UPDATE tutor SET tutors.tutor_first_name=:tutorFirstName, tutors.tutor_last_name=:tutorLastName, tutors.username=:tutorUsername, tutors.password=:tutorPassword WHERE tutor_id = :tutorId ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("tutorFirstName", tutor.getTutorFirstName());
        sqlParameterSource.addValue("tutorLastName", tutor.getTutorLastName());
        sqlParameterSource.addValue("tutorUsername", tutor.getUsername());
        sqlParameterSource.addValue("tutorPassword", tutor.getPassword());
        sqlParameterSource.addValue("tutorId", tutor.getTutorId());

        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }
}
