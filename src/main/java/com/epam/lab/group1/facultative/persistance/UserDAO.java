package com.epam.lab.group1.facultative.persistance;

import com.epam.lab.group1.facultative.model.User;
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
public class UserDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String sql;

    public UserDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Optional<User> getById(int id) {
        sql = "SELECT * FROM users WHERE id = :id;";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, new BeanPropertyRowMapper<>(User.class)));
    }

    public void deleteById(int id) {
        sql = "SELECT * FROM users WHERE id = :id;";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public List<User> getList() {
        sql = "SELECT * FROM user";
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
        sql = "UPDATE user SET users.first_name=:firstName, users.last_name=:lastName, users.email=:email, users.password=:password WHERE id = :id ";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource("firstName", user.getFirstName());
        sqlParameterSource.addValue("lastName", user.getLastName());
        sqlParameterSource.addValue("email", user.getEmail());
        sqlParameterSource.addValue("password", user.getPassword());
        sqlParameterSource.addValue("id", user.getId());

        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }
}
