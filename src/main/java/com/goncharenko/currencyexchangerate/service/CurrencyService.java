package com.goncharenko.currencyexchangerate.service;

import com.goncharenko.currencyexchangerate.dto.CurrencyDTO;
import org.springframework.stereotype.Component;

import java.util.List;


public interface CurrencyService {
    CurrencyDTO getById(Long id);

    List<CurrencyDTO> getAll();

    List<CurrencyDTO> getAllCurrenciesByBankId(Long bankId);

    CurrencyDTO create(Long bankId, CurrencyDTO currencyDTO);

    CurrencyDTO update(Long id, CurrencyDTO currencyDTO);

    void delete(Long id);

}
