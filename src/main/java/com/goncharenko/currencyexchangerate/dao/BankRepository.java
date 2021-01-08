package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.Bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends Paginated<Bank> {
    Optional<Bank> getById(Long id);

    List<Bank> getAll();

    Optional<Bank> create(Bank bank);

    Optional<Bank> update(Long id, Bank bank);

    void delete(Long id);

}
