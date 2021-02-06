package com.goncharenko.currencyexchangerate.service.impl;

import com.goncharenko.currencyexchangerate.dao.BankRepository;
import com.goncharenko.currencyexchangerate.dao.CurrencyRepository;
import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Currency;
import com.goncharenko.currencyexchangerate.dto.BankDTO;
import com.goncharenko.currencyexchangerate.dto.CurrencyDTO;
import com.goncharenko.currencyexchangerate.exceptions.ResourceNotFoundException;
import com.goncharenko.currencyexchangerate.service.BankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.goncharenko.currencyexchangerate.dto.CurrencyDTO.convertToDomain;

@Slf4j
@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional
    @Override
    public BankDTO getById(Long id) {
        Bank bank = bankRepository.
                findById(id).
                orElseThrow(() -> {
                    log.debug("There is no bank with id {} ", id);
                    throw new ResourceNotFoundException("Bank with id " + id + " is not found");
                });
        BankDTO bankDTO = BankDTO.convertToDTO(bank);
        bankDTO.setCurrencyDTOList(bank.getCurrencies().stream().map(CurrencyDTO::convertToDTO).collect(Collectors.toList()));
        return bankDTO;
    }

    @Transactional
    @Override
    public List<BankDTO> getAll(String search, String sortField) {
        List<Bank> retrievedBanks;
        if (StringUtils.isEmpty(search)) {
            retrievedBanks = bankRepository.findAll();
            log.info("Retrieved all banks");
        } else {
            //bankRepository.findAllByNameContains(search);
            ExampleMatcher exampleMatcher = ExampleMatcher
                    .matchingAny()
                    .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
            retrievedBanks = bankRepository.findAll(
                    Example.of(new Bank(search, null, null, null, null, search), exampleMatcher),
                    Sort.by(Sort.Direction.DESC, sortField));
        }
        if (CollectionUtils.isEmpty(retrievedBanks)) {
            log.debug("There are no banks in table ");
            Collections.emptyList();
        }

        return retrievedBanks
                .stream()
                .map(bank -> {
                    BankDTO bankDTO = BankDTO.convertToDTO(bank);
                    bankDTO.setCurrencyDTOList(bank.getCurrencies().stream().map(CurrencyDTO::convertToDTO).collect(Collectors.toList()));
                    return bankDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public BankDTO create(BankDTO bankDTO) {
        Bank bankToBeSaved = BankDTO.convertToDomain(bankDTO);
        List<Currency> currencyList = bankDTO.getCurrencyDTOList()
                .stream()
                .map(currencyDTO -> {
                    Currency currency = convertToDomain(currencyDTO);
                    currency.setBank(bankToBeSaved);
                    return currency;
                })
                .collect(Collectors.toList());
        bankToBeSaved.setCurrencies(currencyList);
        var createdBank = bankRepository.save(bankToBeSaved);

        var convertDtoBank = BankDTO.convertToDTO(createdBank);
        convertDtoBank.setCurrencyDTOList(createdBank.getCurrencies()
                .stream()
                .map(CurrencyDTO::convertToDTO)
                .collect(Collectors.toList()));
        log.info("{} was created", bankDTO);
        return convertDtoBank;

    }

    @Transactional
    @Override
    public BankDTO update(Long id, BankDTO retrievedBankDTO) {
        Bank bankToBeSaved = bankRepository.findById(id).map(bank -> {

            bank.setName(retrievedBankDTO.getName());
            bank.setPhoneNumber(retrievedBankDTO.getPhoneNumber());
            bank.setBankType(retrievedBankDTO.getBankType());
            bank.setIsOnlineAvailable(retrievedBankDTO.getIsOnlineAvailable());
            bank.setNumberOfDepartments(retrievedBankDTO.getNumberOfDepartments());
            bank.setAddress(retrievedBankDTO.getAddress());
            bank.setCurrencies(retrievedBankDTO.getCurrencyDTOList().stream()
                    .map(currencyDTO -> {
                        Currency currency = convertToDomain(currencyDTO);
                        currency.setBank(bank);
                        return currency;
                    }).collect(Collectors.toList()));
            return bank;
        }).orElseThrow(() -> new ResourceNotFoundException("Bank with id " + id + " is not found"));

        var updatedBank = bankRepository.save(bankToBeSaved);
        BankDTO convertDtoBank = BankDTO.convertToDTO(updatedBank);
        convertDtoBank.setCurrencyDTOList(updatedBank.getCurrencies()
                .stream()
                .map(CurrencyDTO::convertToDTO)
                .collect(Collectors.toList()));
        return convertDtoBank;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        bankRepository.findById(id).ifPresentOrElse(bank -> {
            bankRepository.delete(bank);
            log.info("Bank with id {} was deleted", id);
        }, () -> {
            log.debug("Bank was not deleted");
            throw new ResourceNotFoundException("Bank with id " + id + " is not found");
        });
    }
}
