package com.goncharenko.currencyexchangerate.service;

import com.goncharenko.currencyexchangerate.dto.CurrencyDto;

import java.util.List;


public interface CurrencyService {
    CurrencyDto getById(Long id);

    List<CurrencyDto> getAll();

    List<CurrencyDto> getAllCurrenciesByBankId(Long bankId, String search, String sortField);

    CurrencyDto create(Long bankId, CurrencyDto currencyDTO);

    CurrencyDto update(Long id, CurrencyDto currencyDTO);

    void delete(Long id);

}
