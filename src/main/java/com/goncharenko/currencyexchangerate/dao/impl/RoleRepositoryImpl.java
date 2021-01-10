package com.goncharenko.currencyexchangerate.dao.impl;

import com.goncharenko.currencyexchangerate.dao.RoleRepository;
import com.goncharenko.currencyexchangerate.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private static final RowMapper<Role> ROW_MAPPER = new BeanPropertyRowMapper<>(Role.class);
    private final JdbcTemplate jdbcTemplate;

    public RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Role> getRoles(Long userId) {
        log.debug("get all roles by useri d {} ", userId);
        return jdbcTemplate.query("select r.id, r.name from roles r\n"
                + "inner join users_roles ur on r.id = ur.role_id\n"
                + "where ur.user_id = ?", ROW_MAPPER, userId);
    }
}
