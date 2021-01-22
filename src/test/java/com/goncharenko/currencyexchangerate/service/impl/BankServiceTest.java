package com.goncharenko.currencyexchangerate.service.impl;

import com.goncharenko.currencyexchangerate.dao.impl.BankRepositoryImpl;
import com.goncharenko.currencyexchangerate.dao.impl.CurrencyRepositoryImpl;
import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Currency;
import com.goncharenko.currencyexchangerate.domain.Type;
import com.goncharenko.currencyexchangerate.dto.BankDTO;
import com.goncharenko.currencyexchangerate.dto.CurrencyDTO;
import com.goncharenko.currencyexchangerate.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BankServiceTest {

    private List<Bank> banks;
    private List<BankDTO> bankDTO;
    @InjectMocks
    private BankServiceImpl bankService;

    @Mock
    private BankRepositoryImpl bankRepository;

    @Mock
    private CurrencyRepositoryImpl currencyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        banks = new ArrayList<>();
        Bank bankMono = new Bank();
        String name = "MonoBank";
        String phoneNumber = "+380578787333";
        Type bankType = Type.LOCAL;
        Boolean isOnlineAvailable = Boolean.TRUE;
        Integer numberOfDepartments = 500;
        String address = "Kharkiv";

        bankMono.setName(name);
        bankMono.setPhoneNumber(phoneNumber);
        bankMono.setBankType(bankType);
        bankMono.setOnlineAvailable(isOnlineAvailable);
        bankMono.setNumberOfDepartments(numberOfDepartments);
        bankMono.setAddress(address);

        banks.add(bankMono);
        Bank bankPrivat = new Bank("PrivatBank", "+380578899000",
                Type.GLOBAL, Boolean.TRUE, 12000, "Dnepr");
        banks.add(bankPrivat);

        BankDTO bankDTOMono = BankDTO.convertToDTO(bankMono);
        BankDTO bankDTOPrivat = BankDTO.convertToDTO(bankPrivat);
        bankDTO = new ArrayList<>();

        bankDTO.add(bankDTOMono);
        bankDTO.add(bankDTOPrivat);


        bankDTOPrivat.setCurrencyDTOList(Arrays.asList
                (new CurrencyDTO(1l, "Dollar USA", "USD", 26.75, 27.23),
                        new CurrencyDTO(2l, "EURO", "EUR", 33.75, 34.55),
                        new CurrencyDTO(3l, "British pound", "GBP", 38.20, 40.25)));
    }

    @Test
    void createBank_bankDtoWithoutCurrencies_shouldReturnNewBank() {

        Long id = 2l;
        when(bankRepository.create(banks.get(0)))
                .thenAnswer(invocation -> {
                    ((Bank) invocation.getArgument(0)).setId(id);
                    return of(invocation.getArgument(0));
                });

        BankDTO bankDTO = BankDTO.convertToDTO(banks.get(0));

        BankDTO createdBank = bankService.create(bankDTO);

        assertNotNull(createdBank);
        assertNotNull(createdBank.getId());
        assertEquals("MonoBank", createdBank.getName());
        assertEquals("Kharkiv", createdBank.getAddress());
        assertNotNull(createdBank.getCurrencyDTOList());
        assertEquals(0, createdBank.getCurrencyDTOList().size());
        verify(bankRepository).create(any(Bank.class));
        verify(currencyRepository, never()).create(eq(id), any(Currency.class));
    }

    @Test
    void createBank_bankDtoWitCurrencies_shouldReturnNewBank() {

        long generatedBankId = 2L;

        when(bankRepository.create(banks.get(1)))
                .thenAnswer(invocation -> {
                    ((Bank) invocation.getArgument(0)).setId(generatedBankId);
                    return of(invocation.getArgument(0));
                });
        when(currencyRepository.create(eq(generatedBankId), any(Currency.class)))
                .thenAnswer(invocation -> {
                    ((Currency) invocation.getArgument(1)).setId(66L);
                    return of(invocation.getArgument(1));
                });


        BankDTO createdBank = bankService.create(bankDTO.get(1));

        assertNotNull(createdBank);
        assertNotNull(createdBank.getId());
        assertEquals("PrivatBank", createdBank.getName());
        assertEquals("Kharkiv", createdBank.getAddress());
        assertNotNull(createdBank.getCurrencyDTOList());
        assertEquals(3, createdBank.getCurrencyDTOList().size());
        verify(bankRepository).create(any(Bank.class));
        verify(currencyRepository, times(3)).create(eq(generatedBankId), any(Currency.class));
    }

    @Test
    void getByIdBank_bank_shouldReturnBank() {
        Long bankToRetrieve = 3L;

        BankDTO bankDTO = BankDTO.convertToDTO(banks.get(0));
        bankDTO.setCurrencyDTOList(Arrays.asList(new CurrencyDTO(1l, "Dollar USA", "USD", 26.75, 27.23),
                new CurrencyDTO(2l, "EURO", "EUR", 33.75, 34.55),
                new CurrencyDTO(3l, "British pound", "GBP", 38.20, 40.25)));
        Bank bank = BankDTO.convertToDomain(bankDTO);

        when(bankRepository.getById(bankToRetrieve)).thenReturn(of(bank));

        BankDTO actualBank = bankService.getById(bankToRetrieve);

        verify(bankRepository).getById(bankToRetrieve);
        assertEquals("MonoBank", actualBank.getName());
        assertEquals("Kharkiv", actualBank.getAddress());
        assertEquals(0, actualBank.getCurrencyDTOList().size());

    }

    @Test
    void getByIdBank_bank_shouldReturnResourceNotFoundException() {
        Long bankToRetrieve = 33L;

        when(bankRepository.getById(bankToRetrieve)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bankService.getById(bankToRetrieve));

        verify(bankRepository).getById(bankToRetrieve);

    }

    @Test
    void getAll_correctData_shouldReturnAllBanks() {
        when(bankRepository.getAll()).thenReturn(banks);

        List<BankDTO> banks = bankService.getAll();

        assertEquals(2, banks.size());
        assertEquals("MonoBank", banks.get(0).getName());
        assertEquals("Dnepr", banks.get(1).getAddress());
    }

    @Test
    void getAll_EmptyList_shouldThrowResourceNotFoundException() {
        when(bankRepository.getAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> bankService.getAll());
    }

    @Test
    void update_existedBank_shouldReturnUpdatedBank() {
        Long id = 1l;
        when(bankRepository.update(id, banks.get(0)))
                .thenAnswer(invocation -> {
                    ((Bank) invocation.getArgument(0)).setId(id);
                    return of(invocation.getArgument(0));
                });

        Bank bankForUpdate = new Bank();
        String name = "AlfaBank";
        String phoneNumber = "+380555900033";
        Type bankType = Type.GLOBAL;
        Boolean isOnlineAvailable = Boolean.TRUE;
        Integer numberOfDepartments = 900;
        String address = "Kiev";

        bankForUpdate.setName(name);
        bankForUpdate.setPhoneNumber(phoneNumber);
        bankForUpdate.setBankType(bankType);
        bankForUpdate.setOnlineAvailable(isOnlineAvailable);
        bankForUpdate.setNumberOfDepartments(numberOfDepartments);
        bankForUpdate.setAddress(address);
        when(bankRepository.update(id, bankForUpdate))
                .thenAnswer(invocation -> {
                    ((Bank) invocation.getArgument(0)).setId(id);
                    return of(invocation.getArgument(0));
                });


        BankDTO bankDTOForUpdate = BankDTO.convertToDTO(bankForUpdate);

        bankService.update(id, bankDTOForUpdate);

    }

    @Test
    void updated_notExistedBank_shouldThrowResourceNotFoundException() {

        Long bankId = 1L;
        BankDTO bankDtoToUpdate = bankDTO.get(0);
        when(bankRepository.getById(bankId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bankService.update(bankId, bankDtoToUpdate));

        verify(bankRepository, never()).getById(bankId);
    }

    @Test
    void deleteBank_existedBank_shouldExecuteDeleteMethods() {
        Long bankToDelete = 77L;
        when(bankRepository.getById(bankToDelete)).thenReturn(of(new Bank()));

        bankService.delete(bankToDelete);

        verify(bankRepository).delete(bankToDelete);
        verify(currencyRepository).deleteAllByBankId(bankToDelete);
    }

    @Test
    void deleteCat_notExistedCat_shouldThrowResourceNotFoundException() {
        Long bankToDelete = 77L;
        when(bankRepository.getById(bankToDelete)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bankService.delete(bankToDelete));

        verify(bankRepository, never()).delete(bankToDelete);
        verify(currencyRepository, never()).deleteAllByBankId(bankToDelete);
    }
}