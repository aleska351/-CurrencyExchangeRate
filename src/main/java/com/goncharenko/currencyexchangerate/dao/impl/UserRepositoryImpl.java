package com.goncharenko.currencyexchangerate.dao.impl;

import com.goncharenko.currencyexchangerate.dao.UserRepository;
import com.goncharenko.currencyexchangerate.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final RowMapper<User> ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> getByName(String username) {
        LOGGER.debug("get user with name {} ", username);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from users where name = ?",
                    ROW_MAPPER, username));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("Empty result, no user with this name ");
            return Optional.empty();
        }
    }
}
