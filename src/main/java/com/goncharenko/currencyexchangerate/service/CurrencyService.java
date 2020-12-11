package com.goncharenko.currencyexchangerate.service;

import com.goncharenko.currencyexchangerate.dto.CurrencyDTO;

import java.util.List;


public interface CurrencyService {
    CurrencyDTO retrieveById(Long id);

    List<CurrencyDTO> retrieveAll();

    List<CurrencyDTO> retrieveAllCurrenciesByBankId(Long bankId);

    CurrencyDTO create(Long bankId, CurrencyDTO currencyDTO);

    CurrencyDTO update(Long id, CurrencyDTO currencyDTO);

    void delete(Long id);

    void deleteByBankId(Long bankId);

}
