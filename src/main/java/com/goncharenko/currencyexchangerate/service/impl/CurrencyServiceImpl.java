package com.goncharenko.currencyexchangerate.service.impl;

import com.goncharenko.currencyexchangerate.dao.BankRepository;
import com.goncharenko.currencyexchangerate.dao.CurrencyRepository;
import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Currency;
import com.goncharenko.currencyexchangerate.dto.CurrencyDto;
import com.goncharenko.currencyexchangerate.exceptions.ResourceNotFoundException;

import com.goncharenko.currencyexchangerate.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final BankRepository bankRepository;


    @Transactional
    public CurrencyDto getById(Long id) {
        Currency currency = currencyRepository.findById(id).orElseThrow(
                () ->
                {
                    log.debug("There is no currency with id {} ", id);
                    throw new ResourceNotFoundException("There are no currencies with id " + id);
                });
        log.info("Currency with id {} was retrieved", id);
        return CurrencyDto.convertToDTO(currency);
    }

    @Transactional
    public List<CurrencyDto> getAll() {
        List<Currency> currencies;
        currencies = currencyRepository.findAll();
        log.info("Retrieved all currencies");
        return currencies.stream().map(CurrencyDto::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<CurrencyDto> getAllCurrenciesByBankId(Long bankId, String search, String sortField) {
        Bank bank = bankRepository.findById(bankId).orElseThrow();
        List<Currency> currencies;

        if (StringUtils.isEmpty(search)) {
            currencies = bank.getCurrencies();
        } else {
        currencies = currencyRepository.findAllByPurchaseOrSale(search, search);
        }
        log.info("Retrieved all currencies with bank id {} ", bankId);
        return currencies.stream().map(CurrencyDto::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CurrencyDto create(Long bankId, CurrencyDto currencyDTO) {
        if (bankRepository.findById(bankId).isPresent()) {
            Currency createdCurrency = CurrencyDto.convertToDomain(currencyDTO);
            createdCurrency.setBank(bankRepository.findById(bankId).get());
            Currency createdCurrencyc = currencyRepository.save(createdCurrency);
            log.info("{} was created", currencyDTO);
            return CurrencyDto.convertToDTO(createdCurrencyc);
        }
        log.debug("{} can't be created ", currencyDTO);
        throw new ResourceNotFoundException("Bank with id " + bankId + " is not found");
    }

    @Transactional
    @Override
    public CurrencyDto update(Long id, CurrencyDto currencyDTO) {
        Currency currencyToBeUpdated = currencyRepository.findById(id).get();
        currencyToBeUpdated.setName(currencyDTO.getName());
        currencyToBeUpdated.setShortName(currencyDTO.getShortName());
        currencyToBeUpdated.setPurchase(currencyDTO.getPurchase());
        currencyToBeUpdated.setSale(currencyDTO.getSale());

        Currency updatedCurrency = currencyRepository.save(currencyToBeUpdated);
        CurrencyDto updatedCurrencyDto = CurrencyDto.convertToDTO(updatedCurrency);
        return updatedCurrencyDto;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        currencyRepository.findById(id).ifPresentOrElse(currency -> {
            currencyRepository.delete(currency);
            log.info("Currency with id {} was deleted", id);
        }, () -> {
            log.debug("Currency with id {} cannot be delete", id);
            throw new ResourceNotFoundException("Currency with id " + id + " is not found");
        });
    }
}