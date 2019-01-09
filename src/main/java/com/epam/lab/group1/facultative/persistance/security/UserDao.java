package com.epam.lab.group1.facultative.persistance.security;

import com.epam.lab.group1.facultative.dto.UserDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;

public class UserDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public UserDto getUserByEmail(String email) {
        String sql = "SELECT email, password, jobPosition FROM tutors WHERE email = :email";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("email", email);
        UserDto userDto = namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, UserDto.class);
        return userDto;
    }
}
