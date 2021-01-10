package com.goncharenko.currencyexchangerate.service.impl;

import com.goncharenko.currencyexchangerate.dao.BankRepository;
import com.goncharenko.currencyexchangerate.dao.CurrencyRepository;
import com.goncharenko.currencyexchangerate.domain.Currency;
import com.goncharenko.currencyexchangerate.dto.CurrencyDTO;
import com.goncharenko.currencyexchangerate.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements com.goncharenko.currencyexchangerate.service.CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final BankRepository bankRepository;


    @Transactional
    public CurrencyDTO getById(Long id) {
        Currency currency = currencyRepository.getById(id).orElseThrow(
                () ->
                {
                    log.debug("There is no currency with id {} ", id);
                    throw new ResourceNotFoundException("There are no currencies with id " + id);
                });
        log.info("Currency with id {} was retrieved", id);
        return CurrencyDTO.convertToDTO(currency);
    }

    @Transactional
    public List<CurrencyDTO> getAll() {
        List<Currency> currencies = currencyRepository.getAll();
        if (CollectionUtils.isEmpty(currencies)) {
            log.debug("There are no currencies in table ");
            throw new ResourceNotFoundException("There are no currencies in table");
        }
        log.info("Retrieved all currencies");
        return currencies.stream().map(CurrencyDTO::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<CurrencyDTO> getAllCurrenciesByBankId(Long bankId) {
        List<Currency> currencies = currencyRepository.getAllCurrenciesByBankId(bankId);
        if (CollectionUtils.isEmpty(currencies)) {
            log.debug("There are no currencies with  bank id {} in table ", bankId);
            throw new ResourceNotFoundException("There are no currencies with this bank id ");
        }
        log.info("Retrieved all currencies with bank id {} ", bankId);
        return currencies.stream().map(CurrencyDTO::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CurrencyDTO create(Long bankId, CurrencyDTO currencyDTO) {
        if (bankRepository.getById(bankId).isPresent()) {
            Optional<Currency> createdCurrency = currencyRepository.create(bankId, CurrencyDTO.convertToDomain(currencyDTO));
            log.info("{} was created", currencyDTO);
            return CurrencyDTO.convertToDTO(createdCurrency.get());
        }
        log.debug("{} can't be created ", currencyDTO);
        throw new ResourceNotFoundException("Bank with id " + bankId + " is not found");
    }

    @Transactional
    @Override
    public CurrencyDTO update(Long bankId, CurrencyDTO currencyDTO) {
        Optional<Currency> updatedCurrency = currencyRepository.update(bankId, CurrencyDTO.convertToDomain(currencyDTO));
        log.info("{} was updated", currencyDTO);
        return CurrencyDTO.convertToDTO(updatedCurrency.orElseThrow(() -> {
            log.debug("{} can't be updated ", currencyDTO);
            throw new ResourceNotFoundException("Bank with id " + bankId + " is not found");
        }));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        currencyRepository.getById(id).ifPresentOrElse(currency -> {
            currencyRepository.delete(id);
            log.info("Currency with id {} was deleted", id);
        }, () -> {
            log.debug("Currency with id {} cannot be delete", id);
            throw new ResourceNotFoundException("Currency with id " + id + " is not found");
        });
    }
}