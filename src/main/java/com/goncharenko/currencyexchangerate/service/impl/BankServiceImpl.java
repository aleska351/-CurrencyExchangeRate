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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    public List<BankDTO> getAll() {
        List<Bank> retrievedBanks = bankRepository.findAll();
        if (CollectionUtils.isEmpty(retrievedBanks)) {
            log.debug("There are no banks in table ");
            throw new ResourceNotFoundException("There are no banks in table");
        }
        List<BankDTO> bankDTOList = new ArrayList<>();
        for (Bank bank : retrievedBanks) {
            BankDTO bankDTO = BankDTO.convertToDTO(bank);
            bankDTO.setCurrencyDTOList(bank.getCurrencies().stream().map(CurrencyDTO::convertToDTO).collect(Collectors.toList()));
            bankDTOList.add(bankDTO);
        }

        log.info("Retrieved all banks");
        return bankDTOList;
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
        Bank bankToBeUpdated = bankRepository.findById(id).get();

        bankToBeUpdated.setName(retrievedBankDTO.getName());
        bankToBeUpdated.setPhoneNumber(retrievedBankDTO.getPhoneNumber());
        bankToBeUpdated.setBankType(retrievedBankDTO.getBankType());
        bankToBeUpdated.setIsOnlineAvailable(retrievedBankDTO.getIsOnlineAvailable());
        bankToBeUpdated.setNumberOfDepartments(retrievedBankDTO.getNumberOfDepartments());
        bankToBeUpdated.setAddress(retrievedBankDTO.getAddress());
        bankToBeUpdated.setCurrencies(retrievedBankDTO.getCurrencyDTOList().stream()
                .map(currencyDTO -> {
                    Currency currency = convertToDomain(currencyDTO);
                    currency.setBank(bankToBeUpdated);
                    return currency;
                })
                .collect(Collectors.toList()));

        var updatedBank = bankRepository.save(bankToBeUpdated);
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
