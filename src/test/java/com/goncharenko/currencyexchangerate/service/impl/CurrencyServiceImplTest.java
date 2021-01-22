package com.goncharenko.currencyexchangerate.service.impl;

import com.goncharenko.currencyexchangerate.dao.impl.BankRepositoryImpl;
import com.goncharenko.currencyexchangerate.dao.impl.CurrencyRepositoryImpl;
import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Currency;
import com.goncharenko.currencyexchangerate.dto.CurrencyDTO;
import com.goncharenko.currencyexchangerate.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurrencyServiceImplTest {

    private List<Currency> currencies;
    private List<CurrencyDTO> currenciesDTO;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Mock
    private BankRepositoryImpl bankRepository;

    @Mock
    private CurrencyRepositoryImpl currencyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        currencies = new ArrayList<>();
        Currency currencyUSD = new Currency();
        String name = "Dollar USA";
        String shortName = "USD";
        Double purchase = 26.75;
        Double sale = 27.23;
        Long bankId = 1l;


        currencyUSD.setName(name);
        currencyUSD.setShortName(shortName);
        currencyUSD.setPurchase(purchase);
        currencyUSD.setSale(sale);
        currencyUSD.setId(bankId);

        currencies.add(currencyUSD);
        Currency currencyEURO = new Currency("EURO", "EUR", 33.75, 34.55, 1l);
        currencies.add(currencyEURO);

        CurrencyDTO currencyDTOUSD = CurrencyDTO.convertToDTO(currencyUSD);
        CurrencyDTO currencyDTOEURO = CurrencyDTO.convertToDTO(currencyEURO);

        currenciesDTO = new ArrayList<>();
        currenciesDTO.add(currencyDTOUSD);
        currenciesDTO.add(currencyDTOEURO);
    }

    @Test
    void createCurrency_Currency_shouldReturnNewCurrency() {
        Long bankId = 1l;
        Long currencyID = 2l;
        when(currencyRepository.create(bankId, currencies.get(0)))
                .thenAnswer(invocation -> {
                    ((Bank) invocation.getArgument(0)).setId(currencyID);
                    return of(invocation.getArgument(0));
                });

        CurrencyDTO currencyDTOUSD = currenciesDTO.get(0);

        CurrencyDTO createdCurrency = currencyService.create(bankId, currencyDTOUSD);

        assertNotNull(createdCurrency);
        assertNotNull(createdCurrency.getId());
        assertEquals("Dollar USA", createdCurrency.getName());
        assertEquals(26.75, createdCurrency.getPurchaseCurrency());
        verify(currencyRepository).create(bankId, any(Currency.class));
    }


    @Test
    void getById_existedCurrency_shouldReturnCurrency() {
        Long currencyToRetrieve = 3L;

        CurrencyDTO currencyDTO = CurrencyDTO.convertToDTO(currencies.get(0));
        Currency currency = CurrencyDTO.convertToDomain(currencyDTO);

        when(currencyRepository.getById(currencyToRetrieve)).thenReturn(of(currency));

        CurrencyDTO actualCurrency = currencyService.getById(currencyToRetrieve);

        verify(bankRepository).getById(currencyToRetrieve);
        assertEquals("Dollar USA", actualCurrency.getName());
        assertEquals(26.75, actualCurrency.getPurchaseCurrency());

    }

    @Test
    void getById_notExistedCurrency_shouldThrowResourceNotFoundException() {
        Long currencyId = 1L;
        when(currencyRepository.getById(currencyId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.getById(currencyId));
    }


    @Test
    void getAll_correctData_shouldReturnAllCurrencies() {
        when(currencyRepository.getAll()).thenReturn(currencies);

        List<CurrencyDTO> currencies = currencyService.getAll();

        assertEquals(2, currencies.size());
        assertEquals("Dollar USA", currencies.get(0).getName());
        assertEquals(33.75, currencies.get(1).getPurchaseCurrency());
    }

    @Test
    void getAll_EmptyList_shouldThrowResourceNotFoundException() {
        when(currencyRepository.getAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.getAll());
    }

    @Test
    void update_ExistedCurrency_shouldThrowUpdatedCurrency() {
        Long id = 1l;
        Long bankId = 1L;
        when(currencyRepository.update(bankId, currencies.get(0)))
                .thenAnswer(invocation -> {
                    ((Bank) invocation.getArgument(0)).setId(id);
                    return of(invocation.getArgument(0));
                });

        Currency currencyForUpdate = new Currency();
        String name = "Zzz";
        String shortName = "Z";
        shortName = "Z";
        Double purchase = 6.75;
        Double sale = 7.23;

        currencyForUpdate.setName(name);
        currencyForUpdate.setShortName(shortName);
        currencyForUpdate.setPurchase(purchase);
        currencyForUpdate.setSale(sale);

        when(currencyRepository.update(id, currencyForUpdate))
                .thenAnswer(invocation -> {
                    ((Bank) invocation.getArgument(0)).setId(id);
                    return of(invocation.getArgument(0));
                });


        CurrencyDTO currencyDTOForUpdate = CurrencyDTO.convertToDTO(currencyForUpdate);

        currencyService.update(bankId, currencyDTOForUpdate);
    }

    @Test
    void update_notExistedCurrency_shouldThrowResourceNotFoundException() {
        Long currencyId = 1L;
        Long bankId = 1L;
        CurrencyDTO currencyDtoToUpdate = currenciesDTO.get(0);
        when(currencyRepository.getById(currencyId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.update(bankId, currencyDtoToUpdate));

        verify(currencyRepository, never()).getById(currencyId);

    }

    @Test
    void deleteCurrency_existedCurrency_shouldExecuteDeleteMethods() {
        Long currencyToDelete = 77L;
        when(currencyRepository.getById(currencyToDelete)).thenReturn(of(new Currency()));

        currencyService.delete(currencyToDelete);

        verify(currencyRepository).delete(currencyToDelete);
        verify(currencyRepository, times(1)).delete(currencyToDelete);
    }

    @Test
    void deleteCat_notExistedCat_shouldThrowResourceNotFoundException() {
        Long bankToDelete = 77L;
        when(bankRepository.getById(bankToDelete)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.delete(bankToDelete));

        verify(bankRepository, never()).delete(bankToDelete);
        verify(currencyRepository, never()).deleteAllByBankId(bankToDelete);
    }
}