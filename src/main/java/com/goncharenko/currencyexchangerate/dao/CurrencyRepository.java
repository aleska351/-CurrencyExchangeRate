package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Currency;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {

    Optional<Currency> retrieveById(Long id);

    Optional<List<Currency>> retrieveAll();

    Optional<List<Currency>> retrieveAllCurrenciesByBankId(Long bankId);

    Optional<Currency> create(Long bankId, Currency currency);

    Optional<Currency> update(Long id, Currency currency);

    void delete(Long id);

    void deleteAllByBankId(Long bankId);
}
