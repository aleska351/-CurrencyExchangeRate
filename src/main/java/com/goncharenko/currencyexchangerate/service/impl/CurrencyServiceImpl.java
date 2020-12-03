package com.goncharenko.currencyexchangerate.service.impl;

import com.goncharenko.currencyexchangerate.dao.BankRepository;
import com.goncharenko.currencyexchangerate.dao.CurrencyRepository;
import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Currency;
import com.goncharenko.currencyexchangerate.dto.CurrencyDTO;
import com.goncharenko.currencyexchangerate.exceptions.ResourceNotFoundException;
import com.goncharenko.currencyexchangerate.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CurrencyServiceImpl implements CurrencyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankServiceImpl.class);
    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, BankRepository bankRepository) {
        this.currencyRepository = currencyRepository;
        this.bankRepository = bankRepository;
    }

    private final BankRepository bankRepository;

    @Transactional
    @Override
    public CurrencyDTO retrieveById(Long id) {
        Currency currency = currencyRepository.retrieveById(id).orElseThrow(
                () ->
                {
                    LOGGER.debug("There is no currency with id {} ", id);
                    throw new ResourceNotFoundException("There are no currencies in this bank with id " + id);
                });
        LOGGER.info("Currency with id {} was retrieved" + id);
        return CurrencyDTO.convertToDTO(currency);
    }

    @Transactional
    @Override
    public List<CurrencyDTO> retrieveAll() {

        List<Currency> banks = currencyRepository.retrieveAll().orElseThrow(() -> {
            LOGGER.debug("There are no currencies in table ");
            throw new ResourceNotFoundException("There are no currencies in table");
        });
        LOGGER.info("Retrieved all currencies");
        return banks.stream().map(CurrencyDTO::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<CurrencyDTO> retrieveAllCurrenciesByBankId(Long bankId) {
        List<Currency> banks = currencyRepository.retrieveAllCurrenciesByBankId(bankId).orElseThrow(() -> {
            LOGGER.debug("There are no currencies with  bank id {} in table ", bankId);
            throw new ResourceNotFoundException("There are no currencies with this bank id ");
        });
        LOGGER.info("Retrieved all currencies with bank id {} ", bankId);
        return banks.stream().map(CurrencyDTO::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CurrencyDTO create(Long bankId, CurrencyDTO currencyDTO) {
        if (bankRepository.retrieveById(bankId).isPresent()) {
            Optional<Currency> createdCurrency = currencyRepository.create(bankId, CurrencyDTO.convertToDomain(currencyDTO));
            return CurrencyDTO.convertToDTO(createdCurrency.get());
        }
        LOGGER.debug("{} can't be created ", currencyDTO);
        throw new ResourceNotFoundException("Bank with id " + bankId + " is not found");
    }

    @Transactional
    @Override
    public CurrencyDTO update(Long bankId, CurrencyDTO currencyDTO) {
        Optional<Currency> updatedCurrency = currencyRepository.update(bankId, CurrencyDTO.convertToDomain(currencyDTO));
        return CurrencyDTO.convertToDTO(updatedCurrency.orElseThrow(() -> {
            LOGGER.debug("{} can't be updated ", currencyDTO);
            throw new ResourceNotFoundException("Bank with id " + bankId + " is not found");
        }));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        currencyRepository.retrieveById(id).ifPresentOrElse(currency -> {
            currencyRepository.delete(id);
        }, () -> {
            throw new ResourceNotFoundException("Currency with id " + id + " is not found");
        });
    }
}
