package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends Paginated<Currency> {

    Optional<Currency> getById(Long id);

    List<Currency> getAll();

    List<Currency> getAllCurrenciesByBankId(Long bankId);

    Optional<Currency> create(Long bankId, Currency currency);

    Optional<Currency> update(Long id, Currency currency);

    void delete(Long id);

    void deleteAllByBankId(Long bankId);
}
