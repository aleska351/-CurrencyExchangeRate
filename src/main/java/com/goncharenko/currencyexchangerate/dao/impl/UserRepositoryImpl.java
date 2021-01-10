package com.goncharenko.currencyexchangerate.dao.impl;

import com.goncharenko.currencyexchangerate.dao.UserRepository;
import com.goncharenko.currencyexchangerate.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final RowMapper<User> ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<User> getByName(String username) {

        log.debug("get user with name {} ", username);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from users where name = ?",
                    ROW_MAPPER, username));
        } catch (EmptyResultDataAccessException e) {
            log.debug("Empty result, no user with this name ");
            return Optional.empty();
        }
    }
}
