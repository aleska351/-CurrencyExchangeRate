package com.goncharenko.currencyexchangerate.events;

import com.goncharenko.currencyexchangerate.dao.BankRepository;
import com.goncharenko.currencyexchangerate.dao.CurrencyRepository;
import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Currency;
import com.goncharenko.currencyexchangerate.dto.BankDTO;
import com.goncharenko.currencyexchangerate.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ApplicationReadyEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventListener.class);
    private final BankRepository bankRepository;
    private final CurrencyRepository currencyRepository;


    public ApplicationReadyEventListener(BankRepository bankRepository, CurrencyRepository currencyRepository) {
        LOGGER.info("=====================ReadyListener==============================");
        this.bankRepository = bankRepository;
        this.currencyRepository = currencyRepository;
    }

    /*  @Bean
     @EventListener(ApplicationReadyEventListener.class)
     public void applicationReady() {
   /*         LOGGER.info("==============Application Ready======================");

             LOGGER.info("Bank with id 1 " + bankRepository.retrieveById(1L).get());
             LOGGER.info("All banks  " + bankRepository.retrieveAll().get());
             Bank createdBank = bankRepository.create(new Bank("Omega", "+3456536890", Bank.Type.GLOBAL, true, 67, "Dnepr")).get();
             LOGGER.info("Created bank " + createdBank);
             Bank updatedBank = bankRepository.update(createdBank.getId(), new Bank("Omega", "+3456536890", Bank.Type.GLOBAL, true, 67, "Poltava")).get();
             LOGGER.info("Updated bank " + updatedBank);
             bankRepository.delete(updatedBank.getId());


             LOGGER.info("Currency with id 1 " + currencyRepository.retrieveById(1L));
             LOGGER.info("All currencies " + currencyRepository.retrieveAll());
             LOGGER.info("All currencies by given id " + currencyRepository.retrieveAllCurrenciesByBankId(2L));
             Currency createdCurrency = currencyRepository.create(updatedBank.getId(), new Currency("BitCoin", "Coin", 55.0, 56.5, 2L)).get();
             LOGGER.info("Created Currency " + createdCurrency);
             Currency updatedCurrency = currencyRepository.update(createdCurrency.getId(), new Currency("BitCoin", "Coin", 43355.0, 54356.5, 2L)).get();
             LOGGER.info("Updated currency " + updatedCurrency);
             currencyRepository.delete(updatedCurrency.getId());
             currencyRepository.deleteAllByBankId(3L);


         }
        Currency createdCurrency = currencyRepository.create(2l, new Currency("BitCoin2", "Coin", 55.0, 56.5)).get();
        LOGGER.info("Created Currency " + createdCurrency);
    }*/
}