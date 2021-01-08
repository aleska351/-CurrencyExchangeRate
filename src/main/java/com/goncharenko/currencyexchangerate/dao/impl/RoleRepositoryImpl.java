package com.goncharenko.currencyexchangerate.dao.impl;

import com.goncharenko.currencyexchangerate.dao.RoleRepository;
import com.goncharenko.currencyexchangerate.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleRepositoryImpl.class);
    private static final RowMapper<Role> ROW_MAPPER = new BeanPropertyRowMapper<>(Role.class);
    private final JdbcTemplate jdbcTemplate;

    public RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Role> getRoles(Long userId) {
        LOGGER.debug("get all roles by useri d {} ", userId);
        return jdbcTemplate.query("select r.id, r.name from roles r\n"
                + "inner join users_roles ur on r.id = ur.role_id\n"
                + "where ur.user_id = ?", ROW_MAPPER, userId);
    }
}
