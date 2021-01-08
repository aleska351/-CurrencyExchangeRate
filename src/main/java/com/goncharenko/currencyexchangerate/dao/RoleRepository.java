package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.Role;

import java.util.List;

public interface RoleRepository {
    List<Role> getRoles(Long id);
}
