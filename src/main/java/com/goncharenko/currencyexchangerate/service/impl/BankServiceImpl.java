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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.goncharenko.currencyexchangerate.dto.CurrencyDTO.convertToDTO;
import static com.goncharenko.currencyexchangerate.dto.CurrencyDTO.convertToDomain;

@Slf4j
@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final CurrencyRepository currencyRepository;

    @Override
    @Transactional
    public BankDTO create(BankDTO bankDTO) {
        Bank createdBank = bankRepository.create(BankDTO.convertToDomain(bankDTO)).orElseThrow(() -> {
            log.debug("{} cannot be created", bankDTO);
            throw new ResourceNotFoundException("Bank with id  is not found");
        });
        BankDTO convertDtoBank = BankDTO.convertToDTO(createdBank);
        convertDtoBank.setCurrencyDTOList(new ArrayList<>());
        if (!CollectionUtils.isEmpty(bankDTO.getCurrencyDTOList())) {
            bankDTO.getCurrencyDTOList().forEach(currency -> {
                Currency createdCurrency = currencyRepository.create(createdBank.getId(), convertToDomain(currency)).get();
                CurrencyDTO convertedCurrencyDTO = convertToDTO(createdCurrency);
                convertDtoBank.getCurrencyDTOList().add(convertedCurrencyDTO);
            });
        }
        log.info("{} was created", bankDTO);
        return convertDtoBank;
    }

    @Transactional
    @Override
    public BankDTO getById(Long id) {
        Bank bank = bankRepository.getById(id).orElseThrow(() -> {
            log.debug("There is no bank with id {} ", id);
            throw new ResourceNotFoundException("Bank with id " + id + " is not found");
        });

        List<Currency> currencies = currencyRepository.getAllCurrenciesByBankId(id);
        if (CollectionUtils.isEmpty(currencies)) {
            log.debug("There are no currencies with bank id {} ", id);
        }
        BankDTO bankDTO = BankDTO.convertToDTO(bank);
        bankDTO.setCurrencyDTOList(currencies.stream().map(CurrencyDTO::convertToDTO).collect(Collectors.toList()));
        log.info("Bank with id {} was retrieved", id);
        return bankDTO;
    }

    @Transactional
    @Override
    public List<BankDTO> getAll() {
        List<Bank> retrievedBanks = bankRepository.getAll();
        if (CollectionUtils.isEmpty(retrievedBanks)) {
            log.debug("There are no banks in table ");
            throw new ResourceNotFoundException("There are no banks in table");
        }
        List<BankDTO> bankDTOList = retrievedBanks.stream().map(BankDTO::convertToDTO).collect(Collectors.toList());
        for (BankDTO retrievedBankDTO : bankDTOList) {
            List<Currency> currencies = currencyRepository.getAllCurrenciesByBankId(retrievedBankDTO.getId());
            if (CollectionUtils.isEmpty(currencies)) {
                log.debug("There are no currencies in this bank {} ", retrievedBankDTO);
            }
            retrievedBankDTO.setCurrencyDTOList(currencies.stream().map(CurrencyDTO::convertToDTO).collect(Collectors.toList()));
        }
        log.info("Retrieved all banks");
        return bankDTOList;

    }

    @Transactional
    @Override
    public BankDTO update(Long bankDtoId, BankDTO retrievedBankDTO) {
        Optional<Bank> updatedBank = bankRepository.update(bankDtoId, BankDTO.convertToDomain(retrievedBankDTO));
        log.info("{} with id {}  was updated", retrievedBankDTO, bankDtoId);
        return BankDTO.convertToDTO(updatedBank.orElseThrow(() -> {
            log.debug("{} can't be updated ", retrievedBankDTO);
            throw new ResourceNotFoundException("Bank with id " + bankDtoId + " is not found");
        }));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        bankRepository.getById(id).ifPresentOrElse(bank -> {
            currencyRepository.deleteAllByBankId(id);
            bankRepository.delete(id);
            log.info("Bank with id {} was deleted", id);
        }, () -> {
            log.debug("Bank was not deleted");
            throw new ResourceNotFoundException("Bank with id " + id + " is not found");
        });
    }
}
