package com.goncharenko.currencyexchangerate.service;

import com.goncharenko.currencyexchangerate.dto.BankDTO;
import org.springframework.stereotype.Component;

import java.util.List;


public interface BankService {
    BankDTO retrieveById(Long id);

    List<BankDTO> retrieveAll();

    BankDTO create(BankDTO bankDTO);

    BankDTO update(Long id, BankDTO bankDTO);

    void delete(Long id);
}
