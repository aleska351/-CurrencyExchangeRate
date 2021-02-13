package com.goncharenko.currencyexchangerate.service;

import com.goncharenko.currencyexchangerate.dto.BankDto;

import java.util.List;


public interface BankService {
    BankDto getById(Long id);

    List<BankDto> getAll(String search, String sortFie);

    BankDto create(BankDto bankDTO);

    BankDto update(Long id, BankDto bankDTO);

    void delete(Long id);
}
