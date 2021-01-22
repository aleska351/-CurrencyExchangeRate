package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.Bank;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BankRepository extends Paginated<Bank> {
    Optional<Bank> getById(Long id);

    List<Bank> getAll();

    Optional<Bank> create(Bank bank);

    Optional<Bank> update(Long id, Bank bank);

    void delete(Long id);

}
