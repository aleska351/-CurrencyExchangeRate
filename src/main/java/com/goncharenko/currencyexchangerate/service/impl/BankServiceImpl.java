package com.goncharenko.currencyexchangerate.service.impl;

import com.goncharenko.currencyexchangerate.dao.BankRepository;
import com.goncharenko.currencyexchangerate.dao.CurrencyRepository;
import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Currency;
import com.goncharenko.currencyexchangerate.dto.BankDto;
import com.goncharenko.currencyexchangerate.dto.CurrencyDto;
import com.goncharenko.currencyexchangerate.exceptions.ResourceNotFoundException;
import com.goncharenko.currencyexchangerate.service.BankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static com.goncharenko.currencyexchangerate.dto.CurrencyDto.convertToDomain;

@Slf4j
@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional
    @Override
    public BankDto getById(Long id) {
        Bank bank = bankRepository.
                findById(id).
                orElseThrow(() -> {
                    log.debug("There is no bank with id {} ", id);
                    throw new ResourceNotFoundException("Bank with id " + id + " is not found");
                });
        BankDto bankDTO = BankDto.convertToDTO(bank);
        bankDTO.setCurrencyDtoList(bank.getCurrencies().stream().map(CurrencyDto::convertToDTO).collect(Collectors.toList()));
        return bankDTO;
    }

    @Transactional
    @Override
    public List<BankDto> getAll(String search, String sortField) {
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
                    BankDto bankDTO = BankDto.convertToDTO(bank);
                    bankDTO.setCurrencyDtoList(bank.getCurrencies().stream().map(CurrencyDto::convertToDTO).collect(Collectors.toList()));
                    return bankDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public BankDto create(BankDto bankDTO) {
        Bank bankToBeSaved = BankDto.convertToDomain(bankDTO);
        List<Currency> currencyList = bankDTO.getCurrencyDtoList()
                .stream()
                .map(currencyDTO -> {
                    Currency currency = convertToDomain(currencyDTO);
                    currency.setBank(bankToBeSaved);
                    return currency;
                })
                .collect(Collectors.toList());
        bankToBeSaved.setCurrencies(currencyList);
        var createdBank = bankRepository.save(bankToBeSaved);

        var convertDtoBank = BankDto.convertToDTO(createdBank);
        convertDtoBank.setCurrencyDtoList(createdBank.getCurrencies()
                .stream()
                .map(CurrencyDto::convertToDTO)
                .collect(Collectors.toList()));
        log.info("{} was created", bankDTO);
        return convertDtoBank;

    }

    @Transactional
    @Override
    public BankDto update(Long id, BankDto retrievedBankDto) {
        Bank bankToBeSaved = bankRepository.findById(id).map(bank -> {

            bank.setName(retrievedBankDto.getName());
            bank.setPhoneNumber(retrievedBankDto.getPhoneNumber());
            bank.setBankType(retrievedBankDto.getBankType());
            bank.setIsOnlineAvailable(retrievedBankDto.getIsOnlineAvailable());
            bank.setNumberOfDepartments(retrievedBankDto.getNumberOfDepartments());
            bank.setAddress(retrievedBankDto.getAddress());
            bank.setCurrencies(retrievedBankDto.getCurrencyDtoList().stream()
                    .map(currencyDTO -> {
                        Currency currency = convertToDomain(currencyDTO);
                        currency.setBank(bank);
                        return currency;
                    }).collect(Collectors.toList()));
            return bank;
        }).orElseThrow(() -> new ResourceNotFoundException("Bank with id " + id + " is not found"));

        var updatedBank = bankRepository.save(bankToBeSaved);
        BankDto convertDtoBank = BankDto.convertToDTO(updatedBank);
        convertDtoBank.setCurrencyDtoList(updatedBank.getCurrencies()
                .stream()
                .map(CurrencyDto::convertToDTO)
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
