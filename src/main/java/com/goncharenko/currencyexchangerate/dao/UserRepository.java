package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getByName(String username);
}
