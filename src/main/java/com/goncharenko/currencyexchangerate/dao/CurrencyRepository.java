package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Currency;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    List<Currency> findAllByNameContains(String partName);

    List<Currency> findAllByCreatedDateAfter(Instant date);

    List<Currency> findAllByPurchaseOrSale(String purchase, String sale);

    List<Currency> findAllByBank(Bank bank);




    //List<Currency> getAllByBankIdSearchAndSort(Example<Currency> example, Sort sort);

}
