package com.goncharenko.currencyexchangerate.service;

import com.goncharenko.currencyexchangerate.dto.BankDTO;

import java.util.List;


public interface BankService {
    BankDTO getById(Long id);

    List<BankDTO> getAll(String search, String sortFie);

    BankDTO create(BankDTO bankDTO);

    BankDTO update(Long id, BankDTO bankDTO);

    void delete(Long id);
}
