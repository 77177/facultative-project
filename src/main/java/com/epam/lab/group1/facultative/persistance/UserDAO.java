package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String sql;

    public UserDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Optional<User> getById(int id) {
        sql = String.format("SELECT * FROM users WHERE id = %d;", id);
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class));
        } finally {
            return Optional.ofNullable(user);
        }
    }

    public Optional<User> getByEmail(String email) {
        sql = String.format("SELECT * FROM users WHERE id = %s;", email);
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class));
        } finally {
            return Optional.ofNullable(user);
        }
    }

    public void deleteById(int id) {
        sql = String.format("DELETE FROM users WHERE id = %d;", id);
        jdbcTemplate.execute(sql);
    }

    public List<User> getList() {
        sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public User create(User user) {
        sql = "INSERT INTO users (first_name, last_name, email, password) VALUES(:firstName,:lastName,:email,:password);";
        MapSqlParameterSource sqlParameterSource;
        sqlParameterSource = new MapSqlParameterSource("firstName", user.getFirstName());
        sqlParameterSource.addValue("lastName", user.getLastName());
        sqlParameterSource.addValue("email", user.getEmail());
        sqlParameterSource.addValue("password", user.getPassword());
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);

        user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?;"
                , new BeanPropertyRowMapper<>(User.class), user.getEmail());
        return user;
    }

    public void update(User user) {
        sql = "UPDATE users SET first_name=:firstName, last_name=:lastName, email=:email, password=:password WHERE id = :id ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("firstName", user.getFirstName());
        sqlParameterSource.addValue("lastName", user.getLastName());
        sqlParameterSource.addValue("email", user.getEmail());
        sqlParameterSource.addValue("password", user.getPassword());
        sqlParameterSource.addValue("id", user.getId());

        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }
}
